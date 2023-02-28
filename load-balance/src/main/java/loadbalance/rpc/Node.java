package loadbalance.rpc;

/**
 * @author alex.fang
 * @date 2023/1/17
 */
public interface Node {
     String getUrl();

     int getWeight();
}
