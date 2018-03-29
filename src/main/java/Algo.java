import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Algo {

    public static void main(String[] args) throws IOException {
        Graph graph = readGraphFromFile("src/main/resources/edges.txt");

        List<Edge> tree = findKruskalMinimumTree(graph);
        int weight = calculateWeight(tree);

        System.out.println("Kruskal Minimum Tree Weight: " + weight);
    }

    public static List<Edge> findKruskalMinimumTree(Graph graph) {
        graph.getEdges().sort(Comparator.comparing(Edge::getWeight));

        int[] treeId = new int[graph.getNodesCount()];

        for (int i = 0; i < graph.getNodesCount(); i++) {
            treeId[i] = i;
        }

        List<Edge> treeEdges = new ArrayList<>();

        for (Edge edge : graph.getEdges()) {
            if (treeId[edge.getStart()] != treeId[edge.getEnd()]) {
                treeEdges.add(edge);
                int oldId = treeId[edge.getEnd()];
                int newId = treeId[edge.getStart()];

                for (int i = 0; i < graph.getNodesCount(); i++) {
                    if (treeId[i] == oldId) {
                        treeId[i] = newId;
                    }
                }
            }
        }

        return treeEdges;
    }

    public static int calculateWeight(List<Edge> edges) {
        int sumWeight = 0;

        for (Edge edge : edges) {
            sumWeight += edge.getWeight();
        }

        return sumWeight;
    }

    public static Graph readGraphFromFile(String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));

        List<Edge> edges = new ArrayList<>();

        for (String line : lines) {
            String[] tokens = line.split(" ");
            Integer edgeStart = Integer.parseInt(tokens[0]);
            Integer edgeEnd = Integer.parseInt(tokens[1]);
            Integer edgeWeight = Integer.parseInt(tokens[2]);

            edges.add(new Edge(edgeStart, edgeEnd, edgeWeight));
        }

        Graph graph = new Graph();
        graph.setEdges(edges);

        return graph;
    }

}
