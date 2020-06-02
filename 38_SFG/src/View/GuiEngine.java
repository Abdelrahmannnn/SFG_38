
package View;

import ComponentsOfGraph.Edge;
import ComponentsOfGraph.Graph;
import ComponentsOfGraph.Node;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Map;

public class GuiEngine {

    private Graph graph = Graph.getGraph();
    private final int diam = 50;
    private final int FONT = 15;


    public int getDiameter() {

        return diam;
    }

    public void refresh(Graphics canvas) {

        int rad = diam / 2;

        ArrayList<Edge> edges = graph.getEdges();
        for (int i = 0; i < edges.size(); i++) {
            Edge edge = edges.get(i);
            Node from = edge.getFrom();
            Node to = edge.getTo();
            Point p1 = (Point) from.getPosition().clone();
            Point p2 = (Point) to.getPosition().clone();
            p1.x += rad;
            p1.y += rad;
            p2.x += rad;
            p2.y += rad;
            if (to.getNode() - from.getNode() == 1) {
                Graphics2D g = (Graphics2D) canvas;
                g.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g.setStroke(new BasicStroke(3));
                g.setColor(Color.blue);
                Point inter = new Point(p1.x + (p2.x - p1.x) / 2, p1.y + (p2.y - p1.y)
                        / 2);
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
                int x = inter.x;
                if (p2.x - p1.x >= 0) {
                    x += 8;
                } else {
                    x -= 8;
                }
                g.fill(new Polygon(new int[] { x, inter.x, inter.x }, new int[] {
                        inter.y, inter.y + 8, inter.y - 8 }, 3));
                g.setFont(new Font("Arial", Font.BOLD, FONT));
                g.drawString(edge.getWeight().toString(), x, inter.y - 10);
            } else if (to.getNode() - from.getNode() == 0) {
                Graphics2D g = (Graphics2D) canvas;
                g.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g.setStroke(new BasicStroke(3));
                g.setColor(Color.black);
                g.drawOval(p1.x - 25, p1.y - 50, 50, 50);
                g.fill(new Polygon(new int[] { p1.x, p1.x, p1.x - 8 }, new int[] {
                        p1.y - 50 + 8, p1.y - 50 - 8, p1.y - 50 }, 3));
                g.setFont(new Font("Arial", Font.BOLD, FONT));
                g.drawString(edge.getWeight().toString(), p1.x - 8, p1.y - 50 - 10);
            } else {
                Graphics2D g = (Graphics2D) canvas;
                g.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g.setStroke(new BasicStroke(3));
                if (to.getNode() - from.getNode() >= 0) {
                    g.setColor(Color.blue);
                } else {
                    g.setColor(Color.ORANGE);
                }
                double angle = Math.atan2(p2.y - p1.y, p2.x - p1.x);
                int diameter = (int) Math.round(p1.distance(p2));

                AffineTransform oldXForm = g.getTransform(); // Reserving the Old Form

                g.translate(p1.x, p1.y);
                g.rotate(angle);
                int h = (int) (diameter * 1.5 / 3);
                h += Math.abs(from.getNode() - to.getNode()) * 3;
                g.drawArc(0, -h / 2, diameter, h, 0, 180);
                h /= 2;
                diameter /= 2;
                g.fill(new Polygon(new int[] { diameter + 8, diameter, diameter },
                        new int[] { -h, -h + 8, -h - 8 }, 3));

                g.setColor(Color.blue);
                g.setFont(new Font("Arial", Font.BOLD, FONT));
                g.drawString(edge.getWeight().toString(), diameter + 1, -h - 5);

                g.setTransform(oldXForm); // Restore transform
            }
        }

        Map<Integer, Node> nodes = graph.getNodes();
        for (int i = 0; i < nodes.size() ; i++) {
            Point point = (Point) nodes.get(i).getPosition().clone();
            point.x += rad;
            point.y += rad;
            Node n = nodes.get(i);
            Graphics2D g = (Graphics2D) canvas;
            g.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(n.getColor());
            g.setStroke(new BasicStroke(5));
            g.drawOval(nodes.get(i).getPosition().x, nodes.get(i).getPosition().y,
                    diam, diam);
            g.setColor(n.getFillColor());
            if (i == 0) {
                g.setColor(Color.pink);
            } else if (i == nodes.size() - 1) {
                g.setColor(Color.pink);
            }
            g.fillOval(nodes.get(i).getPosition().x, nodes.get(i).getPosition().y,
                    diam, diam);
            g.setFont(new Font("Arial", Font.BOLD, FONT));
            g.setColor(Color.black);
            if (i == 0) {
                g.drawString("R", point.x - 3, point.y + 4);
            } else if (i == nodes.size() - 1) {
                g.drawString("C", point.x - 3, point.y + 4);
            } else {
                Integer num = nodes.get(i).getNode();
                g.drawString(num.toString(), point.x - 3, point.y + 4);
            }

        }
    }


}
