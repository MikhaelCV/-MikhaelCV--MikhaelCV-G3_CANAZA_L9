import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.io.*;

import java.util.*;

public class JGraphTExample {
    public static void main(String[] args) {
        // Crear un grafo dirigido ponderado
        Graph<String, DefaultWeightedEdge> graph =
            new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

        // Añadir vértices
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");

        // Añadir aristas con peso
        graph.setEdgeWeight(graph.addEdge("A", "B"), 4);
        graph.setEdgeWeight(graph.addEdge("A", "C"), 2);
        graph.setEdgeWeight(graph.addEdge("B", "D"), 3);
        graph.setEdgeWeight(graph.addEdge("C", "D"), 1);

        // Imprimir el grafo
        System.out.println("Vertices: " + graph.vertexSet());
        System.out.println("Aristas: " + graph.edgeSet());

        // Algoritmo de Dijkstra
        DijkstraShortestPath<String, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<>(graph);
        List<String> shortestPath = dijkstra.getPath("A", "D").getVertexList();
        double distance = dijkstra.getPathWeight("A", "D");

        System.out.println("Camino más corto de A a D: " + shortestPath);
        System.out.println("Distancia total: " + distance);

        // Exportar a DOT (para Graphviz)
        try {
            DOTExporter<String, DefaultWeightedEdge> exporter = new DOTExporter<>(v -> v);
            exporter.setEdgeAttributeProvider(e -> {
                Map<String, Attribute> map = new LinkedHashMap<>();
                map.put("label", DefaultAttribute.createAttribute(String.valueOf(graph.getEdgeWeight(e))));
                return map;
            });

            exporter.exportGraph(graph, System.out);
        } catch (ExportException e) {
            e.printStackTrace();
        }
    }
}
