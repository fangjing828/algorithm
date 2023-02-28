package loadbalance.rpc;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 加权轮询
 *
 * @author alex.fang
 * @date 2023/1/17
 */
public class WeightedRoundRobinLoadBalance extends AbstractLoadBalance {
    ConcurrentMap<String, WeightedRoundRobin> wrrMap = new ConcurrentHashMap<>();

    @Override
    Node doSelect(List<Node> nodes) {
        long now = System.currentTimeMillis();
        long maxCurrent = Long.MIN_VALUE;
        Node selectedNode = null;
        WeightedRoundRobin selectedWrr = null;
        int totalWeight = 0;
        for (Node node : nodes) {
            int weight = node.getWeight();
            totalWeight += weight;
            WeightedRoundRobin wrr = wrrMap.computeIfAbsent(node.getUrl(), k -> {
                WeightedRoundRobin result = new WeightedRoundRobin();
                result.setWeight(weight);
                return result;
            });

            if (weight != wrr.getWeight()) {
                wrr.setWeight(weight);
            }

            long cur = wrr.increaseCurrent();
            wrr.setLastUpdate(now);
            if (cur > maxCurrent) {
                maxCurrent = cur;
                selectedNode = node;
                selectedWrr = wrr;
            }
        }

        if (nodes.size() != wrrMap.size()) {
            wrrMap.entrySet().removeIf((item) -> now - item.getValue().getLastUpdate() > 60000L);
        }

        if (selectedNode != null) {
            selectedWrr.sel(totalWeight);
            return selectedNode;
        } else {
            return nodes.get(0);
        }
    }


    static class WeightedRoundRobin {
        private final AtomicLong current = new AtomicLong(0);
        private long lastUpdate;
        private int weight;

        protected WeightedRoundRobin() {
        }

        public long increaseCurrent() {
            return this.current.addAndGet(this.weight);
        }

        public void sel(int total) {
            this.current.addAndGet(-total);
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
            this.current.set(0);
        }

        public long getLastUpdate() {
            return this.lastUpdate;
        }

        public void setLastUpdate(long lastUpdate) {
            this.lastUpdate = lastUpdate;
        }
    }
}
