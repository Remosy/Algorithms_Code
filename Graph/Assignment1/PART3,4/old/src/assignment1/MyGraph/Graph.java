package assignment1.MyGraph;

import assignment1.Delivery;
import assignment1.MyGraph.Node;
import assignment1.MyGraph.Edge;

import java.util.ArrayList;
import java.util.LinkedList;

public class Graph {
    public int totalNode;
    //public LinkedList<Edge> adjacentList[];
    public ArrayList<Node> adjacentList;

    public Graph(int totalNode){
        this.totalNode = totalNode;
        adjacentList = new ArrayList<>();
        /*for(int i = 0; i<totalNode;i++){
            adjacentList[i] = new LinkedList();
        }*/
    }

    public void addNode(Node node){
        //adjacentList[delivery.source().identifier()].add(delivery);
        adjacentList.add(node);
    }

    public void addEdge(int pos, Edge edge){
        //adjacentList[delivery.source().identifier()].add(delivery);
        adjacentList.get(pos).edges.add(edge);
    }

    public void printGraph(){
        System.out.println("You have graph: ");
        int count = 0;
        //while(count<totalNode){

            for(Node node: adjacentList){
                //if(adjacentList.get(count).edges.isEmpty())System.out.println("Edge: P"+count+ ", NULL_____");
                System.out.println(node.toString());
            }
            /*for(Edge edge: adjacentList[count]){
                System.out.print(edge.toString());
            }*/
            //if(adjacentList.get(count).edges.isEmpty())System.out.print("Edge: P"+count+ ", NULL_____");
            //count++;
            //System.out.println();
        //}

        System.out.println("------Finish Print Graph-------"+'\n');
    }




}
