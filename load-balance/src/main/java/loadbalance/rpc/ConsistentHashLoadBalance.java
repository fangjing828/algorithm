package loadbalance.rpc;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author alex.fang
 * @date 2023/1/28
 */
public class ConsistentHashLoadBalance extends AbstractLoadBalance {
    private ConsistentHashSelector selector;
    private static final ThreadLocal<Object> context = new ThreadLocal<>();

    @Override
    Node doSelect(List<Node> nodes) {
        int identityHashCode = nodes.hashCode();
        if (selector == null || selector.identityHashCode != identityHashCode) {
            selector = new ConsistentHashSelector(nodes, identityHashCode);
        }
        Object ctx = context.get();
        int hash = ctx == null ? 0 : ctx.hashCode();
        return selector.select(hash);
    }

    static final class ConsistentHashSelector {
        private final int identityHashCode;
        private final TreeMap<Integer, Node> virtualNodes = new TreeMap<>();
        private final int replicaNumber = 160;

        public ConsistentHashSelector(List<Node> nodes, int identityHashCode) {
            this.identityHashCode = identityHashCode;
            for (Node node : nodes) {
                for (int i = 0; i < this.replicaNumber; i++) {
                    this.virtualNodes.put((node.getUrl() + "_" + i).hashCode(), node);
                }
            }
        }

        public Node select(int hash) {
            Map.Entry<Integer, Node> entry = this.virtualNodes.ceilingEntry(hash);
            if (entry == null) {
                entry = this.virtualNodes.firstEntry();
            }

            return entry.getValue();
        }
    }
}
