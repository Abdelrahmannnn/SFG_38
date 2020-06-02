package ComponentsOfGraph;

import java.awt.Color;
import java.awt.Point;

public class Node {
    private Integer node;
    private Point position;
    private int Indeg = 0;
    private int Outdeg = 0;
    private Color color;
    private Color fillColor;
    public Node(Integer node ,Point position){
        this.node = node;
        this.position = position;
        color = Color.black;
        fillColor = Color.cyan;
    }
    public Integer getNode() {
        return node;
    }
    public void setNode(Integer node) {
        this.node = node;
    }
    public Point getPosition() {
        return position;
    }
    public void setPosition(Point position) {
        this.position = position;
    }
    public int getIndeg() {
        return Indeg;
    }
    public void setIndeg(int indeg) {
        this.Indeg = indeg;
    }
    public int getOutdeg() {
        return Outdeg;
    }
    public void setOutdeg(int outdeg) {
        this.Outdeg = outdeg;
    }

    public Color getColor() {
        return this.color;
    }

    public Color getFillColor() {
        return this.fillColor;
    }
}
