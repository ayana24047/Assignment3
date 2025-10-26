package edu.assignment3;

import java.util.*;
import java.util.stream.Collectors;

public class PrimAlgorithm {

    public static Map<String, Object> run(Graph graph) {
        long start = System.nanoTime();
        int operations = 0;

        Map<String, List<Graph.Edge>> adj = new HashMap<>();
        for (String v : graph.vertices) adj.put(v, new ArrayList<>());
        for (Graph.Edge e : graph.edges) {
            adj.get(e.u).add(e);
            adj.get(e.v).add(new Graph.Edge(e.v, e.u, e.weight));
        }

        String startNode = graph.vertices.get(0);
        Set<String> visited = new HashSet<>();
        PriorityQueue<Graph.Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));

        visited.add(startNode);
        pq.addAll(adj.get(startNode));

        List<Graph.Edge> mst = new ArrayList<>();
        int totalCost = 0;

        while (!pq.isEmpty() && mst.size() < graph.vertices.size() - 1) {
            Graph.Edge e = pq.poll();
            operations++;
            if (!visited.contains(e.v)) {
                visited.add(e.v);
                mst.add(e);
                totalCost += e.weight;
                for (Graph.Edge next : adj.get(e.v)) {
                    if (!visited.contains(next.v)) pq.add(next);
                }
            }
        }

        long end = System.nanoTime();
        return Map.of(
                "algorithm", "Prim",
                "total_cost", totalCost,
                "operations", operations,
                "execution_time_ms", (end - start) / 1_000_000.0,
                "edges", mst.stream()
                        .map(ed -> ed.u + "-" + ed.v + "(" + ed.weight + ")")
                        .collect(Collectors.toList())
        );
    }
}