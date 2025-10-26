package edu.assignment3;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> resultAll = new LinkedHashMap<>();

        File inputFile = new File(System.getProperty("user.dir") + "/ass_3_input.json");

        if (!inputFile.exists()) {
            System.out.println("⚠️ File ass_3_input.json not found! Place it next to pom.xml");
            return;
        }

        Map<String, List<Graph>> input = mapper.readValue(inputFile, new TypeReference<>() {});
        List<Graph> graphs = input.get("graphs");

        for (Graph g : graphs) {
            System.out.println("\n=== " + g.name + " ===");


            System.out.println("Vertices: " + g.vertices.size());
            System.out.println("Edges: " + g.edges.size());


            var primResult = PrimAlgorithm.run(g);
            var kruskalResult = KruskalAlgorithm.run(g);

            double primTime = (double) primResult.get("execution_time_ms");
            double kruskalTime = (double) kruskalResult.get("execution_time_ms");
            int primOps = (int) primResult.get("operations");
            int kruskalOps = (int) kruskalResult.get("operations");
            int primCost = (int) primResult.get("total_cost");
            int kruskalCost = (int) kruskalResult.get("total_cost");

            // Print metrics
            System.out.println("Prim → cost=" + primCost + ", operations=" + primOps + ", time=" + primTime + " ms");
            System.out.println("Kruskal → cost=" + kruskalCost + ", operations=" + kruskalOps + ", time=" + kruskalTime + " ms");

            resultAll.put(g.name, Map.of(
                    "Prim", primResult,
                    "Kruskal", kruskalResult
            ));
        }

        // Save to output JSON
        File outputFile = new File(System.getProperty("user.dir") + "/ass_3_output.json");
        mapper.writerWithDefaultPrettyPrinter().writeValue(outputFile, resultAll);

        System.out.println("\n Results saved to ass_3_output.json");
    }
}
