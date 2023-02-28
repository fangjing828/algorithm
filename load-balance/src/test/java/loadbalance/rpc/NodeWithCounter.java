package loadbalance.rpc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author alex.fang
 * @date 2023/1/30
 */
public class NodeWithCounter implements Node {
    private static final AtomicInteger URL_GENERATOR = new AtomicInteger();
    private final AtomicInteger counter = new AtomicInteger();

    private String url;
    private int weight;

    public NodeWithCounter() {
        this(0);
    }

    public NodeWithCounter(int weight) {
        this.url = "url_" + URL_GENERATOR.incrementAndGet();
        this.weight = weight;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    public int increment() {
        return counter.incrementAndGet();
    }

    public int getCount() {
        return counter.get();
    }
}
