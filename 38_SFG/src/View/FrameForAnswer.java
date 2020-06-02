package View;

import ComponentsOfGraph.Graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Solve.ForwardPaths;
import Solve.Loops;
import Solve.MasonAlgorithm;

public class FrameForAnswer extends JFrame {

    /**
     *
     */
    private static final long serial = 1L;
    private MasonAlgorithm mason = new MasonAlgorithm();
    private Graph graph = Graph.getGraph();
    private boolean solved;
    private int to;

    FrameForAnswer() {
        solved = true;
        to = -1;

    }

    public void setTo(int val) {
        to = val;
        return;
    }

    public boolean Solved() {
        return solved;
    }

    public void start() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        setBounds(200, 200, 1000, 500);
        setBackground(Color.WHITE);
        setTitle("Solution");

        JLabel answer = new JLabel("Overall T.F. : ");
        answer.setBounds(screenWidth / 5, 20, 250, 50);
        answer.setFont(new Font("Arial", Font.BOLD, 30));
        getContentPane().add(answer);

        JTextField answerField = new JTextField();
        answerField.setBounds(answer.getX() + answer.getWidth(), answer.getY(),
                screenWidth / 3 - 150, answer.getHeight());
        answerField.setFont(new Font("Arial", Font.BOLD, 30));
        answerField.setEditable(false);
        answerField.setBorder(null);
        getContentPane().add(answerField);
        answerField.setColumns(10);

        JLabel deltaLabel = new JLabel("Delta:");
        deltaLabel.setBounds(2 * screenWidth / 3, 20, 90, 50);
        deltaLabel.setFont(new Font("Arial", Font.BOLD, 30));
        getContentPane().add(deltaLabel);

        JTextField deltaField = new JTextField();
        deltaField.setBounds(deltaLabel.getX() + deltaLabel.getWidth(), deltaLabel.getY(),
                screenWidth / 3 - 150, deltaLabel.getHeight());
        deltaField.setFont(new Font("Arial", Font.BOLD, 30));
        deltaField.setEditable(false);
        deltaField.setBorder(null);
        getContentPane().add(deltaField);
        deltaField.setColumns(10);

        JLabel forwardPathsLabel = new JLabel("Forward Paths:");
        forwardPathsLabel.setBounds(0, 100, screenWidth / 6, 50);
        forwardPathsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        getContentPane().add(forwardPathsLabel);

        JTextArea forwardPaths = new JTextArea();
        forwardPaths.setBackground(Color.WHITE);
        forwardPaths.setEditable(false);
        forwardPaths.setBounds(forwardPathsLabel.getX(), forwardPathsLabel.getY()
                + forwardPathsLabel.getHeight(), forwardPathsLabel.getWidth(), 2
                * screenHeight / 4 - forwardPaths.getY());
        forwardPaths.setFont(new Font("Arial", Font.BOLD, 20));
        forwardPaths.setLineWrap(false);

        JLabel pathsGainLabel = new JLabel("Gains:");
        pathsGainLabel.setBounds(
                forwardPathsLabel.getX() + forwardPathsLabel.getWidth(),
                forwardPathsLabel.getY(), screenWidth / 15,
                forwardPathsLabel.getHeight());
        pathsGainLabel.setFont(new Font("Arial", Font.BOLD, 20));
        getContentPane().add(pathsGainLabel);

        JTextArea pathsGain = new JTextArea();
        pathsGain.setBackground(Color.WHITE);
        pathsGain.setEditable(false);
        pathsGain.setBounds(pathsGainLabel.getX(), pathsGainLabel.getY()
                        + pathsGainLabel.getHeight(), pathsGainLabel.getWidth(),
                forwardPaths.getHeight());
        pathsGain.setFont(new Font("Arial", Font.BOLD, 20));
        pathsGain.setLineWrap(false);

        JLabel deltasLabel = new JLabel("Deltas:");
        deltasLabel.setBounds(pathsGainLabel.getX() + pathsGainLabel.getWidth(),
                pathsGainLabel.getY(), forwardPathsLabel.getWidth(),
                forwardPathsLabel.getHeight());
        deltasLabel.setFont(new Font("Arial", Font.BOLD, 20));
        getContentPane().add(deltasLabel);

        JTextArea deltas = new JTextArea();
        deltas.setBackground(Color.WHITE);
        deltas.setEditable(false);
        deltas.setBounds(deltasLabel.getX(),
                deltasLabel.getY() + deltasLabel.getHeight(), forwardPaths.getWidth(),
                forwardPaths.getHeight());
        deltas.setFont(new Font("Arial", Font.BOLD, 20));
        deltas.setLineWrap(false);

        JLabel loopsLabel = new JLabel("Loops:");
        loopsLabel.setBounds(deltasLabel.getX() + deltasLabel.getWidth(),
                deltasLabel.getY(), forwardPathsLabel.getWidth(),
                deltasLabel.getHeight());
        loopsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        getContentPane().add(loopsLabel);

        JTextArea loops = new JTextArea();
        loops.setBackground(Color.WHITE);
        loops.setEditable(false);
        loops.setBounds(loopsLabel.getX(),
                loopsLabel.getY() + loopsLabel.getHeight(), deltas.getWidth(),
                deltas.getHeight());
        loops.setFont(new Font("Arial", Font.BOLD, 20));
        loops.setLineWrap(false);

        JLabel loopsGainLabel = new JLabel("Gains:");
        loopsGainLabel.setBounds(loopsLabel.getX() + loopsLabel.getWidth(),
                loopsLabel.getY(), pathsGainLabel.getWidth(),
                pathsGainLabel.getHeight());
        loopsGainLabel.setFont(new Font("Arial", Font.BOLD, 20));
        getContentPane().add(loopsGainLabel);

        JTextArea loopsGain = new JTextArea();
        loopsGain.setBackground(Color.WHITE);
        loopsGain.setEditable(false);
        loopsGain.setBounds(loopsGainLabel.getX(), loopsGainLabel.getY()
                        + loopsGainLabel.getHeight(), loopsGainLabel.getWidth(),
                loops.getHeight());
        loopsGain.setFont(new Font("Arial", Font.BOLD, 20));
        loopsGain.setLineWrap(false);

        JLabel combinationsLabel = new JLabel("Combinations of non touching loops:");
        combinationsLabel.setBounds(
                loopsGainLabel.getX() + loopsGainLabel.getWidth(),
                loopsGainLabel.getY(), loopsLabel.getWidth() + 200,
                loopsGainLabel.getHeight());
        combinationsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        getContentPane().add(combinationsLabel);

        JTextArea combinations = new JTextArea();
        combinations.setBackground(Color.WHITE);
        combinations.setEditable(false);
        combinations.setBounds(combinationsLabel.getX(), combinationsLabel.getY()
                        + combinationsLabel.getHeight(), combinationsLabel.getWidth(),
                loops.getHeight());
        combinations.setFont(new Font("Arial", Font.BOLD, 20));
        combinations.setLineWrap(false);

        try {
            answerField.setText(mason.solve(0, to).toString());
            deltaField.setText(mason.getDelta().toString());
        } catch (Exception ex) {
            solved = false;
            return;
        }

        JPanel solutionPanel = new JPanel(null);

        StringBuilder S = new StringBuilder();
        StringBuilder Sgains = new StringBuilder();

        ArrayList<ForwardPaths> fPaths = mason.getForwardPaths();
        for (int i = 0; i < fPaths.size(); i++) {
            S.append(i + ") " + fPaths.get(i) + "\n");
            Sgains.append(i + ") " + fPaths.get(i).getM() + "\n");
        }

        forwardPaths.setText(S.toString());
        pathsGain.setText(Sgains.toString());

        JScrollPane pathsScrollPane = new JScrollPane(forwardPaths,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pathsScrollPane.setBounds(forwardPaths.getX(), forwardPaths.getY(),
                forwardPaths.getWidth(), forwardPaths.getHeight());
        solutionPanel.add(pathsScrollPane);

        JScrollPane pathsGainsScrollPane = new JScrollPane(pathsGain,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pathsGainsScrollPane.setBounds(pathsGain.getX(), pathsGain.getY(),
                pathsGain.getWidth(), pathsGain.getHeight());
        solutionPanel.add(pathsGainsScrollPane);

        S = new StringBuilder();
        Sgains = new StringBuilder();

        ArrayList<Loops> lps = mason.getLoops();
        for (int i = 0; i < lps.size(); i++) {
            S.append(i + ") " + lps.get(i) + "\n");
            Sgains.append(i + ") " + lps.get(i).getGain() + "\n");
        }

        loops.setText(S.toString());
        loopsGain.setText(Sgains.toString());

        JScrollPane loopsScrollPane = new JScrollPane(loops,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        loopsScrollPane.setBounds(loops.getX(), loops.getY(), loops.getWidth(),
                loops.getHeight());
        solutionPanel.add(loopsScrollPane);

        JScrollPane loopsGainsScrollPane = new JScrollPane(loopsGain,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        loopsGainsScrollPane.setBounds(loopsGain.getX(), loopsGain.getY(),
                loopsGain.getWidth(), loopsGain.getHeight());
        solutionPanel.add(loopsGainsScrollPane);

        S = new StringBuilder();
        ArrayList<Double> delta = mason.getDeltas();
        for (int i = 0; i < delta.size(); i++) {
            S.append(i + ") " + delta.get(i) + "\n");
        }

        deltas.setText(S.toString());

        JScrollPane deltasScrollPane = new JScrollPane(deltas,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        deltasScrollPane.setBounds(deltas.getX(), deltas.getY(), deltas.getWidth(),
                deltas.getHeight());
        solutionPanel.add(deltasScrollPane);

        S = new StringBuilder();
        HashMap<Integer, ArrayList<ArrayList<Integer>>> combination = mason
                .getCombinations();
        int i = 1;
        while (combination.get(i) != null) {
            ArrayList<ArrayList<Integer>> list = combination.get(i);
            S.append(i + " non touching loops: \n");
            int num = 0;
            for (ArrayList<Integer> j : list) {
                S.append(num + ") Loop(s) # " + j + "\n");
                num++;
            }
            i++;
        }

        combinations.setText(S.toString());

        JScrollPane combinationsScroll = new JScrollPane(combinations,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        combinationsScroll.setBounds(combinations.getX(), combinations.getY(),
                combinations.getWidth(), combinations.getHeight());
        solutionPanel.add(combinationsScroll);

        add(solutionPanel);

    }


}
