package assignment1;

/**
 * This class is for applying the deliveries information onto Edge structure
 *
 * @author Yangyang Xu
 */
public class Edge {
    public Node src;
    public Node dst;

    public Integer arrTime;
    public Integer dptTime;

    public Edge(Node src, Node dst, Integer dstTime, Integer arrTime) {
        this.src = src;
        this.dst = dst;
        this.arrTime = arrTime;
        this.dptTime = dstTime;
    }

    @Override
    public String toString() {
        return "Edge: " + src.location.toString() + ", " + dst.location.toString() + ", " + arrTime + " ," + dptTime + '\t';
    }

}
