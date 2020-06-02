package Solve;
import java.util.ArrayList;


import java.util.HashMap;
import java.util.TreeSet;

import ComponentsOfGraph.Graph;
import ComponentsOfGraph.EdgeComponent;
import ComponentsOfGraph.Node;
public class MasonAlgorithm {
    private ArrayList<Loops>loops;
    private ArrayList<ForwardPaths>forwardPaths;
    private ArrayList<Double>deltas;
    private Double delta = 1.0;
    private HashMap<Integer,ArrayList<ArrayList<Integer>>>combinations;
    private boolean calculated = false;
    public MasonAlgorithm(){
        loops = new ArrayList<Loops>();
        forwardPaths = new ArrayList<ForwardPaths>();
        deltas = new ArrayList<Double>();
        setCombinations(new HashMap<Integer,ArrayList<ArrayList<Integer>>>());
    }
    public Double solve(Integer from ,Integer to){
        if(from.equals(to)){
            throw new RuntimeException("source and destination must be different");
        }
        if(!explore(Graph.getGraph().getNodes().get(from),Graph.getGraph().getNodes().get(to),0)){
            throw new RuntimeException("No paths found");
        }
        Integer inputnode = Graph.getGraph().getsource();
        Integer outputnode = Graph.getGraph().getdestination();

        if(inputnode == -1){
            throw new RuntimeException("Graph is invalid");
        }
        loops.clear();
        for(int i = 0 ; i < Graph.getGraph().getVertices() ; i++){
            ArrayList<Integer>nodes = new ArrayList<Integer>();
            nodes.add(i);
            getCycles(Graph.getGraph().getNodes().get(i),Graph.getGraph().getNodes().get(i),1.0,1<<i,loops,new TreeSet<Integer>(),nodes);
        }
        delta = CalculateDelta();
        calculated = true;

        try{
            if(from.compareTo(inputnode) == 0 || to.compareTo(outputnode) == 0){
                return solver(Graph.getGraph().getNodes().get(from),Graph.getGraph().getNodes().get(to));
            }
            else{
                return solver(Graph.getGraph().getNodes().get(to),Graph.getGraph().getNodes().get(inputnode)) / solver(Graph.getGraph().getNodes().get(from),Graph.getGraph().getNodes().get(inputnode));
            }
        }
        catch(Exception e){
            throw new RuntimeException("No paths found");
        }
    }
    private Double CalculateDelta() {
        Double d = 0.0;
        int tot = 1 << loops.size();
        for(int i = 0 ; i < tot ; i++){
            ArrayList<Loops>list = new ArrayList<Loops>();
            ArrayList<Integer>list1 = new ArrayList<Integer>();
            int j = 0;
            Double totalGain = 1.0;
            for(Loops loop : loops){
                if((i&(1<<j)) != 0){
                    list.add(loop);
                    list1.add(j);
                    totalGain *= loop.getGain();
                }
                j++;
            }
            boolean flag = CheckNonTouchingLoops(list);
            if(!flag){
                continue;
            }
            if(combinations.get(list.size()) == null){
                combinations.put(list.size(), new ArrayList<ArrayList<Integer>>());
            }
            combinations.get(list.size()).add(list1);
            if(list.size()%2 == 0){
                d += totalGain;
            }
            else {
                d -= totalGain;
            }
        }
        return d;
    }

    private boolean CheckNonTouchingLoops(ArrayList<Loops> list) {

        for(int i = 0 ; i < list.size() ; i++){
            for(int j = i+1 ; j < list.size() ; j++){
                if((list.get(i).getMask() & list.get(j).getMask()) != 0){
                    return false;
                }
            }
        }
        return true;
    }
    private boolean explore(Node node, Node node2 , Integer mask) {
        if(node == node2){
            return true;
        }
        ArrayList<EdgeComponent>adj = Graph.getGraph().getAdjacent(node.getNode());
        for(EdgeComponent e : adj){
            Node x = e.getNode();
            if((mask&(1 << x.getNode())) != 0){
                continue;
            }
            if(explore(x,node2,(mask|(1 << x.getNode())))){
                return true;
            }
        }
        return false;
    }
    private Double solver(Node from ,Node to){
        forwardPaths.clear();
        getForwardPaths(from, to, 0 ,forwardPaths ,new ArrayList<Integer>(),1.0);
        if(forwardPaths.size() == 0){
            throw new RuntimeException();
        }
        Double totalTransfer = 0.0;
        deltas.clear();
        for(ForwardPaths path : forwardPaths){
            Double deltaI = 0.0;
            int tot = 1 << loops.size();
            for(int i = 0 ; i < tot ; i++){
                ArrayList<Loops>list = new ArrayList<Loops>();
                int j = 0;
                Double totalGain = 1.0;
                for(Loops loop : loops){
                    if((i&(1<<j)) != 0){
                        if((loop.getMask() & path.getMask()) != 0){
                            break;
                        }
                        list.add(loop);
                        totalGain *= loop.getGain();
                    }
                    j++;
                }
                if(j < loops.size()){
                    continue;
                }
                boolean flag = CheckNonTouchingLoops(list);
                if(!flag){
                    continue;
                }
                if(list.size()%2 == 0){
                    deltaI += totalGain;
                }
                else {
                    deltaI -= totalGain;
                }

            }
            totalTransfer += deltaI * path.getM();
            deltas.add(deltaI);
        }
        return totalTransfer / delta;
    }
    private void getForwardPaths(Node from, Node to, int mask,
                                 ArrayList<ForwardPaths> forwardPaths, ArrayList<Integer> list , Double gain) {
        list.add(from.getNode());
        if(from == to){
            forwardPaths.add(new ForwardPaths((ArrayList<Integer>) list.clone(),gain,mask));
            list.remove(list.size()-1);
            return;
        }
        ArrayList<EdgeComponent>adj = Graph.getGraph().getAdjacent(from.getNode());
        for(EdgeComponent e : adj){
            Node x = e.getNode();
            if((mask&(1 << x.getNode())) != 0){
                continue;
            }
            getForwardPaths(x, to, (mask|(1 << x.getNode())), forwardPaths, list, gain * e.getWeight());
        }
        list.remove(list.size()-1);
    }
    private void getCycles(Node from, Node to, Double gain, int mask,
                           ArrayList<Loops> loops, TreeSet<Integer> set , ArrayList<Integer>nodes) {
        ArrayList<EdgeComponent>adj = Graph.getGraph().getAdjacent(from.getNode());
        for(EdgeComponent e : adj){
            Node q = e.getNode();
            if((mask&(1 << q.getNode())) != 0 && q != to){
                continue;
            }
            else if((mask&(1 << q.getNode())) != 0){
                set.add(e.getEdgeNum());
                Loops loop = new Loops();
                loop.setEdges((TreeSet<Integer>) set.clone());
                loop.setMask(mask | (1 << q.getNode()));
                loop.setNodes((ArrayList<Integer>) nodes.clone());
                loop.setGain(gain*e.getWeight());
                boolean flag = false;
                for(Loops t : loops){
                    if(t.equals(loop)){
                        flag = true;
                        break;
                    }
                }
                if(!flag){
                    loops.add(loop);
                }
                set.remove(e.getEdgeNum());
            }
            else{
                set.add(e.getEdgeNum());
                nodes.add(q.getNode());
                getCycles(q, to, gain * e.getWeight(), mask | (1 << q.getNode()), loops, set , nodes);
                set.remove(e.getEdgeNum());
                nodes.remove(nodes.size()-1);
            }
        }
    }

    public Double getDelta(){
        if(calculated){
            return delta;
        }
        else{
            throw new RuntimeException();
        }
    }
    public ArrayList<ForwardPaths> getForwardPaths(){
        return forwardPaths;
    }
    public ArrayList<Loops>getLoops(){
        return loops;
    }
    public ArrayList<Double>getDeltas() {
        return deltas;
    }
    public HashMap<Integer,ArrayList<ArrayList<Integer>>> getCombinations() {
        return combinations;
    }
    public void setCombinations(HashMap<Integer,ArrayList<ArrayList<Integer>>> combinations) {
        this.combinations = combinations;
    }

}