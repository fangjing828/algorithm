package loadbalance.rpc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author alex.fang
 * @date 2023/1/30
 */
class RandomLoadBalanceTest {
    RandomLoadBalance loadBalance;
    @BeforeEach
    void setUp() {
        loadBalance = new RandomLoadBalance();
    }

    @Test
    void testSelect() {
        List<Node> nodeList = List.of(new NodeWithCounter(), new NodeWithCounter(), new NodeWithCounter());
        int expected = 400;
        int count = 400 * nodeList.size();
        for (int i = 0; i < count; i++) {
            ((NodeWithCounter)loadBalance.select(nodeList)).increment();
        }
        for (Node node : nodeList) {
            assertEquals(expected, ((NodeWithCounter) node).getCount(), 50);
        }
    }
}