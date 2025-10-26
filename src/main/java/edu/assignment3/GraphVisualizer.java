package edu.assignment3;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GraphVisualizer extends JPanel {
    private final Graph graph;
    private final List<Graph.Edge> mstEdges;
    private final int xOffset;
    private final int yOffset;

    public GraphVisualizer(Graph graph, List<Graph.Edge> mstEdges, int xOffset, int yOffset) {
        this.graph = graph;
        this.mstEdges = mstEdges;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        setPreferredSize(new Dimension(600, 400));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));

        int n = graph.vertices.size();
        int radius = 150;
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;


        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            x[i] = cx + (int) (radius * Math.cos(angle));
            y[i] = cy + (int) (radius * Math.sin(angle));
        }


        for (Graph.Edge e : graph.edges) {
            int i = graph.vertices.indexOf(e.u);
            int j = graph.vertices.indexOf(e.v);
            if (mstEdges.contains(e) || mstEdges.stream().anyMatch(edge ->
                    (edge.u.equals(e.v) && edge.v.equals(e.u) && edge.weight == e.weight))) {
                g2.setColor(Color.RED); // MST
            } else {
                g2.setColor(Color.LIGHT_GRAY); // обычные рёбра
            }
            g2.drawLine(x[i], y[i], x[j], y[j]);

            g2.setColor(Color.BLACK);
            int mx = (x[i] + x[j]) / 2;
            int my = (y[i] + y[j]) / 2;
            g2.drawString(String.valueOf(e.weight), mx, my);
        }


        for (int i = 0; i < n; i++) {
            g2.setColor(Color.BLUE);
            g2.fillOval(x[i] - 10, y[i] - 10, 20, 20);
            g2.setColor(Color.BLACK);
            g2.drawString(graph.vertices.get(i), x[i] + 12, y[i]);
        }
    }

    public static void showGraph(Graph graph, List<Graph.Edge> mstEdges, int xOffset, int yOffset) {
        JFrame frame = new JFrame("Graph Visualizer: " + graph.name);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(new GraphVisualizer(graph, mstEdges, xOffset, yOffset));
        frame.pack();
        frame.setLocation(xOffset, yOffset); // смещение окна
        frame.setVisible(true);
    }
}
