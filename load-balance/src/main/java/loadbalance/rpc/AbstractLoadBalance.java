package loadbalance.rpc;

import java.util.List;

/**
 * @author alex.fang
 * @date 2023/1/17
 */
public abstract class AbstractLoadBalance implements LoadBalance {
    @Override
    public Node select(List<Node> nodes) {
        if (nodes == null || nodes.isEmpty()) {
            return null;
        }
        return nodes.size() == 1 ? nodes.get(0) : doSelect(nodes);
    }

    abstract Node doSelect(List<Node> nodes);
}
