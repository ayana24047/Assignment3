package edu.assignment3;

import java.util.*;
import java.util.stream.Collectors;

public class KruskalAlgorithm {

    static class UnionFind {
        private final Map<String, String> parent = new HashMap<>();
        private final Map<String, Integer> rank = new HashMap<>();

        public UnionFind(List<String> vertices) {
            for (String v : vertices) {
                parent.put(v, v);
                rank.put(v, 0);
            }
        }

        public String find(String node) {
            if (!parent.get(node).equals(node)) {
                parent.put(node, find(parent.get(node)));
            }
            return parent.get(node);
        }

        public void union(String u, String v) {
            String rootU = find(u);
            String rootV = find(v);
            if (!rootU.equals(rootV)) {
                int rankU = rank.get(rootU);
                int rankV = rank.get(rootV);
                if (rankU < rankV) parent.put(rootU, rootV);
                else if (rankU > rankV) parent.put(rootV, rootU);
                else {
                    parent.put(rootV, rootU);
                    rank.put(rootU, rankU + 1);
                }
            }
        }
    }

    public static Map<String, Object> run(Graph graph) {
        long start = System.nanoTime();
        int operations = 0;
        List<Graph.Edge> mst = new ArrayList<>();
        List<Graph.Edge> edges = new ArrayList<>(graph.edges);
        edges.sort(Comparator.comparingInt(e -> e.weight));

        UnionFind uf = new UnionFind(graph.vertices);
        int totalCost = 0;

        for (Graph.Edge edge : edges) {
            operations++;
            if (!uf.find(edge.u).equals(uf.find(edge.v))) {
                uf.union(edge.u, edge.v);
                mst.add(edge);
                totalCost += edge.weight;
            }
        }

        long end = System.nanoTime();
        return Map.of(
                "algorithm", "Kruskal",
                "total_cost", totalCost,
                "operations", operations,
                "execution_time_ms", (end - start) / 1_000_000.0,
                "edges", mst.stream()
                        .map(e -> e.u + "-" + e.v + "(" + e.weight + ")")
                        .collect(Collectors.toList())
        );
    }
}
