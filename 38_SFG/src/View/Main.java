package View;

import ComponentsOfGraph.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Main {

    private JFrame frame;
    private JTextField textField;
    private MyPanel panel;
    private Graph graph = Graph.getGraph();
    private GuiEngine engine;
    private Node bgn;
    private Node end;
    private boolean addEdgeBtnPressed;
    private boolean addNodeBtnPressed;
    private int screenWidth, screenHeight;


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main window = new Main();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Main() {
        Begin();
        addEdgeBtnPressed = false;
        addNodeBtnPressed = false;
        engine = new GuiEngine();
    }


    private void Begin() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int) screenSize.getWidth();
        screenHeight = (int) screenSize.getHeight();


        frame = new JFrame();
        frame.setBounds(0, 0, screenWidth, screenHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.WHITE);
        frame.setTitle("Signal Flow Graph");
        frame.getContentPane().setLayout(null);
        panel = new MyPanel();
        panel.setBounds(250, 0, screenWidth - 250, screenHeight);
        frame.getContentPane().add(panel);

        JButton addNode = new JButton("Add Node");
        addNode.setBounds(0, 50, 250, 50);
        addNode.setFont(new Font("Arial", Font.BOLD, 22));
        addNode.setBackground(new Color(220, 220, 220));
        frame.getContentPane().add(addNode);
        addNode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                reset();

                if (graph.getNodes().size() == 0) {
                    JOptionPane.showMessageDialog(panel,
                            "You can drag any node after adding it", "Note",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                textField.setText("Pin the new Node");
                addNodeBtnPressed = true;
            }
        });

        JButton addEdge = new JButton("Add Edge");
        addEdge.setBounds(0, addNode.getY() + addNode.getHeight() + 50, 250, 50);
        addEdge.setBackground(new Color(220, 220, 220));
        addEdge.setFont(new Font("Arial", Font.BOLD, 22));
        frame.getContentPane().add(addEdge);
        addEdge.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textField.setText("Select The First Node");
                addEdgeBtnPressed = true;
            }
        });




        JButton cancel = new JButton("Cancel");
        cancel.setBounds(0, addEdge.getY() + addNode.getHeight() + 50, 250, 50);
        cancel.setBackground(new Color(220, 220, 220));
        cancel.setFont(new Font("Arial", Font.BOLD, 22));
        frame.getContentPane().add(cancel);
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                reset();
            }
        });


        JButton clear = new JButton("Clear");
        clear.setBounds(0, cancel.getY() + cancel.getHeight() + 50, 250, 50);
        clear.setBackground(new Color(220, 220, 220));
        clear.setFont(new Font("Arial", Font.BOLD, 22));
        frame.getContentPane().add(clear);
        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                     reset();
                   graph.getEdges().clear();
                graph.getEdges().removeAll(Collections.singleton(null));
                graph.getEdges().removeAll(Collections.singleton(""));
                    graph.getNodes().clear();
                    graph.getmap().clear();
                   graph.setallnodes();
                    panel.removeAll();
                    panel.repaint();


            }
        });

        textField = new JTextField();
        textField.setBounds(0, clear.getY() + clear.getHeight(), 250, 100);
        textField.setFont(new Font("Arial", Font.BOLD, 20));
        textField.setEditable(false);
        textField.setBorder(null);
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        JButton solve = new JButton("Solve");
        solve.setBounds(0, textField.getY() + textField.getHeight(), 250, 100);
        solve.setBackground(new Color(220, 220, 220));
        solve.setFont(new Font("Arial", Font.BOLD, 22));
        frame.getContentPane().add(solve);
        solve.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JTextField Endnode = new JTextField();
                Object[] message = { "From R to ( Enter node number or c ) :", Endnode };
                String[] buttons = { "Solve", "Cancel" };
                int option = JOptionPane.showOptionDialog(null, message,
                        "Enter node number in one field only", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, buttons, buttons);
                FrameForAnswer solution = new FrameForAnswer();
                if (option == 0) {
                    String to = Endnode.getText();
                    if (to.length() == 0) {
                        JOptionPane.showMessageDialog(panel,
                                "You should enter a node number!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    } else {
                        if (to.toLowerCase().equals("r")) {
                            JOptionPane.showMessageDialog(panel,
                                    "Cannot solve from R to itself!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        if (to.toLowerCase().equals("c")) {
                            solution.setTo(graph.getVertices() - 1);
                        } else {
                            try {
                                Integer x = Integer.parseInt(to);
                                if (x < 0 || x >= graph.getVertices()) {
                                    JOptionPane.showMessageDialog(panel,
                                            "Node number is out of the range!", "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                if (x.equals(0)) {
                                    JOptionPane.showMessageDialog(panel,
                                            "Cannot solve from R to itself!", "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                solution.setTo(x);
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(panel,
                                        "Numbers or (C) only are accepted in input!", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }
                        solution.start();
                    }

                    // AnswerFrame solution = new AnswerFrame();
                    if (!solution.Solved()) {
                        JOptionPane.showMessageDialog(panel, "Graph has got error(s)",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    solution.setVisible(true);
                }
            }
        });



    }

    private void reset() {

        addEdgeBtnPressed = false;
        addNodeBtnPressed = false;
        bgn = null;
        end = null;
        textField.setText("");
    }



    private class MyPanel extends JPanel {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        private MouseDrag mouseDrag;

        private MyPanel() {
            setBackground(Color.WHITE);
            mouseDrag = new MouseDrag();
            addMouseListener(mouseDrag);
            addMouseMotionListener(mouseDrag);
        }

        public boolean isInsideNode(Node node, Point point) {
            return new Ellipse2D.Float(node.getPosition().x, node.getPosition().y,
                    engine.getDiameter(), engine.getDiameter()).contains(point);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            engine.refresh(g);
        }

        private final class MouseDrag extends MouseAdapter {

            private boolean inside;
            private Point last;
            private Node current;

            private MouseDrag() {

                inside = false;
                last = null;
                current = null;
                bgn = null;
                end = null;
            }

            @Override
            public void mousePressed(MouseEvent m) {
                last = m.getPoint();

                if (addNodeBtnPressed) {
                    graph.addNode(new Point(last.x - (engine.getDiameter()) / 2, last.y
                            - (engine.getDiameter()) / 2));
                    reset();
                } else {
                    Map<Integer, Node> nodes = graph.getNodes();
                    for (int i = nodes.size() - 1; i >= 0; i--) {
                        inside = isInsideNode(nodes.get(i), last);
                        if (inside) {
                            if (!addEdgeBtnPressed) { // dragging
                                current = nodes.get(i);
                            } else { // selecting
                                if (bgn == null) {
                                    textField.setText("Select The Second Node");
                                    bgn = nodes.get(i);
                                } else {
                                    end = nodes.get(i);
                                    String input = JOptionPane.showInputDialog(null,
                                            "Enter the Gain:", "Input", JOptionPane.QUESTION_MESSAGE);
                                    Double gain = 0.0;
                                    try {
                                        gain = Double.parseDouble(input);
                                    } catch (Exception e) {
                                        reset();
                                        return;
                                    }
                                    if (end.getNode() == 0) {
                                        JOptionPane.showMessageDialog(panel,
                                                "R node can't have an in edge", "Error",
                                                JOptionPane.ERROR_MESSAGE);
                                        reset();
                                        return;
                                    }
                                    ArrayList<Edge> edges = graph.getEdges();
                                    for (int iter = 0; iter < edges.size(); iter++) {
                                        if (edges.get(iter).getFrom() == bgn
                                                && edges.get(iter).getTo() == end) {
                                            JOptionPane.showMessageDialog(panel,
                                                    "Edge is already present", "Error",
                                                    JOptionPane.ERROR_MESSAGE);
                                            reset();
                                            return;
                                        }
                                    }
                                    graph.addEdge(bgn.getNode(), end.getNode(), gain);
                                    reset();
                                }
                            }
                            break;
                        }
                    }
                }
                panel.repaint();
            }

            @Override
            public void mouseDragged(MouseEvent m) {
                int dx = m.getX() - last.x;
                int dy = m.getY() - last.y;
                if (inside && !addEdgeBtnPressed) {
                    current.setPosition(new Point(current.getPosition().x + dx, current
                            .getPosition().y + dy));
                }
                last = m.getPoint();
                panel.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent m) {
                last = null;
                inside = false;
                current = null;
                panel.repaint();
            }


        }
    }
}
