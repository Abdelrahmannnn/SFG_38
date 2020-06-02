package Solve;

import java.util.ArrayList;
import java.util.TreeSet;

public class Loops implements Cloneable,Comparable{
    private TreeSet<Integer>edges;
    private Integer mask = 0;
    private Double gain = 0.0;
    private ArrayList<Integer>nodes;
    public Loops(){
        setEdges(new TreeSet<Integer>());
        setNodes(new ArrayList<Integer>());
    }
    public TreeSet<Integer> getEdges() {
        return edges;
    }
    public void setEdges(TreeSet<Integer> edges) {
        this.edges = edges;
    }
    public Integer getMask() {
        return mask;
    }
    public void setMask(Integer mask) {
        this.mask = mask;
    }
    public boolean equals(Loops loop){
        return (edges.toString().compareTo(loop.getEdges().toString()) == 0) ? true : false;
    }
    public Double getGain() {
        return gain;
    }
    public void setGain(Double gain) {
        this.gain = gain;
    }
    public Object clone(){
        return this;
    }
    public String toString(){
        if(nodes.size() == 0)return "";
        String ans = "";
        for(Integer i : nodes){
            ans += i + " ";
        }
        ans += nodes.get(0);
        return ans;
    }
    @Override
    public int compareTo(Object o) {
        if(equals(o)){
            return 0;
        }
        return -1;
    }
    public ArrayList<Integer> getNodes() {
        return nodes;
    }
    public void setNodes(ArrayList<Integer> nodes) {
        this.nodes = nodes;
    }
}
