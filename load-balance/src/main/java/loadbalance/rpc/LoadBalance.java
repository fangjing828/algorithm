package loadbalance.rpc;

import java.util.List;

/**
 * @author alex.fang
 * @date 2023/1/17
 */
public interface LoadBalance {
    Node select(List<Node> nodes);
}
