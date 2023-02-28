package loadbalance.rpc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author alex.fang
 * @date 2023/1/30
 */
class WeightedRandomLoadBalanceTest {
    WeightedRandomLoadBalance loadBalance;

    @BeforeEach
    void setUp() {
        loadBalance = new WeightedRandomLoadBalance();
    }

    @Test
    void testSelect() {
        List<Node> nodeList = List.of(new NodeWithCounter(3), new NodeWithCounter(2), new NodeWithCounter(1));
        int expected = 200;
        int totalWeight = nodeList.stream()
                .mapToInt(Node::getWeight)
                .sum();
        int count = expected * totalWeight;
        for (int i = 0; i < count; i++) {
            ((NodeWithCounter) loadBalance.select(nodeList)).increment();
        }
        for (Node node : nodeList) {
            NodeWithCounter nodeWithCounter = (NodeWithCounter) node;
            assertEquals(expected * nodeWithCounter.getWeight(), nodeWithCounter.getCount(), 50);
        }
    }
}