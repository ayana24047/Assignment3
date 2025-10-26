package edu.assignment3;

import java.util.List;

public class Graph {
    public String name;
    public List<String> vertices;
    public List<Edge> edges;

    public static class Edge {
        public String u;
        public String v;
        public int weight;

        public Edge() {}

        public Edge(String u, String v, int weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }
    }
}