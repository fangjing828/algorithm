package loadbalance.rpc;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机
 * @author alex.fang
 * @date 2023/1/17
 */
public class RandomLoadBalance extends AbstractLoadBalance {
    @Override
    Node doSelect(List<Node> nodes) {
        return nodes.get(ThreadLocalRandom.current().nextInt(nodes.size()));
    }
}
