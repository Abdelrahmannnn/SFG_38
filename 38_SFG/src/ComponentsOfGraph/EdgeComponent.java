package ComponentsOfGraph;

public class EdgeComponent {
    private Node node;
    private Double Weight;
    private Integer edgeNum;
    public EdgeComponent(Node node ,Double signal ,Integer edgeNum){
        this.node = node;
        this.Weight = signal;
        this.edgeNum = edgeNum;
    }
    public Node getNode() {
        return node;
    }
    public void setNode(Node node) {
        this.node = node;
    }
    public Double getWeight() {
        return Weight;
    }
    public void setWeight(Double weight) {
        this.Weight = weight;
    }
    public Integer getEdgeNum(){
        return edgeNum;
    }
}
