package assignment1.MyGraph;

import assignment1.Location;

public class Edge {
    public Node src;
    public Node dst;
    //public Node parent;
    public Integer arrTime;
    public Integer dptTime;
    //public Integer arrivalTime;
    //public Integer departureTime;

    public Edge(Node src, Node dst, Integer dstTime, Integer arrTime){
        this.src = src;
        this.dst = dst;
        this.arrTime = arrTime;
        this.dptTime = dstTime;
        //this.parent = null;
        //this.arrivalTime = Integer.MAX_VALUE;
        //this.departureTime = Integer.MIN_VALUE;
    }

    @Override
    public String toString() {
        return "Edge: "+ src.location.toString()+", "+dst.location.toString()+", "+arrTime+" ,"+dptTime+'\t';
    }

}
