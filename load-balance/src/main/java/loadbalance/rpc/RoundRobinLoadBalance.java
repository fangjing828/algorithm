package loadbalance.rpc;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 轮询
 * @author alex.fang
 * @date 2023/1/17
 */
public class RoundRobinLoadBalance extends AbstractLoadBalance {
    ConcurrentMap<String, RoundRobin> roundRobinMap = new ConcurrentHashMap<>();

    @Override
    Node doSelect(List<Node> nodes) {
        long now = System.currentTimeMillis();
        long maxCurrent = Long.MIN_VALUE;
        Node selectedNode = null;
        RoundRobin selectedRr = null;
        int totalWeight = nodes.size();
        for (Node node : nodes) {
            RoundRobin rr = roundRobinMap.computeIfAbsent(node.getUrl(), key -> new RoundRobin());
            long cur = rr.increaseCurrent();
            rr.setLastUpdate(now);
            if (cur > maxCurrent) {
                maxCurrent = cur;
                selectedNode = node;
                selectedRr = rr;
            }
        }

        // 清理已经下线的节点
        if (nodes.size() != roundRobinMap.size()) {
            roundRobinMap.entrySet().removeIf((item) -> now - item.getValue().getLastUpdate() > 60000L);
        }

        if (selectedNode != null) {
            selectedRr.sel(totalWeight);
            return selectedNode;
        } else {
            return nodes.get(0);
        }
    }

    static class RoundRobin {
        private final AtomicLong current = new AtomicLong(0L);
        private long lastUpdate;

        protected RoundRobin() {
        }

        public long increaseCurrent() {
            return this.current.incrementAndGet();
        }

        public void sel(int total) {
            this.current.addAndGet(-total);
        }

        public long getLastUpdate() {
            return this.lastUpdate;
        }

        public void setLastUpdate(long lastUpdate) {
            this.lastUpdate = lastUpdate;
        }
    }
}
