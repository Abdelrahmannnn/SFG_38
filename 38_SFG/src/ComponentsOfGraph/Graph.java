package ComponentsOfGraph;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph {
   private   ArrayList<Edge>edges;
    private int AllNodes = 0;
   private   Map<Node,ArrayList<EdgeComponent>>map;
   private   Map<Integer,Node>nodes;
    private static Graph graph = null;


    public static Graph getGraph(){
        if(graph == null){
            graph = new Graph();
        }
        return graph;
    }
    public Graph(){
        edges = new ArrayList<Edge>();
        map = new HashMap<Node,ArrayList<EdgeComponent>>();
        nodes = new HashMap<Integer,Node>();
    }
    public void addNode(Point pos){
        Node node = new Node(AllNodes,pos);
        ArrayList<EdgeComponent>list = new ArrayList<EdgeComponent>();
        map.put(node, list);
        nodes.put(AllNodes, node);
        AllNodes++;
    }
    public void addEdge(int from ,int to ,Double weight){
        Node x = nodes.get(from);
        Node y = nodes.get(to);
        x.setOutdeg(x.getOutdeg()+1);
        y.setIndeg(y.getIndeg()+1);
        Edge edge = new Edge(x,y,weight);
        edges.add(edge);
        EdgeComponent e = new EdgeComponent(y,weight,edges.size());
        map.get(x).add(e);
    }
    public ArrayList<EdgeComponent>getAdjacent(int x){
        return map.get(nodes.get(x));
    }
    public int getVertices(){
        return AllNodes;
    }
    public int getNumEdges(){
        return edges.size();
    }
    public Map<Node, ArrayList<EdgeComponent>> getmap(){
        return map;
    }
    public Map<Integer,Node>getNodes(){
        return nodes;
    }
    public ArrayList<Edge>getEdges(){
        return edges;
    }
    public Integer getsource(){
        for(Map.Entry<Integer,Node>entry : nodes.entrySet()){
            if(entry.getValue().getIndeg() == 0){
                return entry.getKey();
            }
        }
        return -1;
    }
    public Integer getdestination(){
        for(Map.Entry<Integer,Node>entry : nodes.entrySet()){
            if(entry.getValue().getOutdeg() == 0){
                return entry.getKey();
            }
        }
        return -1;
    }

    public void Set (){
        edges = new ArrayList<Edge>();
        map = new HashMap<Node,ArrayList<EdgeComponent>>();
        nodes = new HashMap<Integer,Node>();
    }
    public void setallnodes () {
        AllNodes = 0 ;
    }
}
