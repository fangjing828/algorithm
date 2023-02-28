package loadbalance.rpc;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 加权随机
 * @author alex.fang
 * @date 2023/1/17
 */
public class WeightedRandomLoadBalance extends AbstractLoadBalance {
    @Override
    Node doSelect(List<Node> nodes) {
        int length = nodes.size();
        int totalWeight = 0;
        int[] weights = new int[length];
        boolean sameWeight = true;
        for (int i = 0; i < length; i++) {
            int weight = nodes.get(i).getWeight();
            totalWeight += weight;
            weights[i] = totalWeight;
            if (sameWeight && totalWeight != weight * (i + 1)) {
                sameWeight = false;
            }
        }

        if (totalWeight > 0 && !sameWeight) {
            int offset = ThreadLocalRandom.current().nextInt(totalWeight);
            for (int i = 0; i < length; i++) {
                if (offset < weights[i]) {
                    return nodes.get(i);
                }
            }
        }

        return nodes.get(ThreadLocalRandom.current().nextInt(length));
    }
}
