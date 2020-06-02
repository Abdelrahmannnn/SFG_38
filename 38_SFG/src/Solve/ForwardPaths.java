package Solve;

import java.util.ArrayList;

public class ForwardPaths implements Cloneable{
    private ArrayList<Integer>path = new ArrayList<Integer>();
    private Double M;
    private Integer mask = 0;
    public ForwardPaths(ArrayList<Integer>list , Double gain , Integer mask){
        path = list;
        M = gain;
        this.mask = mask;
    }
    public Double getM(){
        return M;
    }
    public ArrayList<Integer> getPath(){
        return path;
    }
    public Integer getMask() {
        return mask;
    }
    public void setMask(Integer mask) {
        this.mask = mask;
    }
    public Object clone(){
        return this;
    }
    public String toString(){
        String answer = "" ;
        for(Integer i : path){
            answer += i.toString() + " " ;
        }
        return answer;
    }
}
