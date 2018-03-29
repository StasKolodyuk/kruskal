import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Algo {

    public static void main(String[] args) throws IOException {
        Graph graph = readGraphFromFile("src/main/resources/edges.txt");

        findKruskalMinimumTree(graph);

        graph.display();
    }

    static void addEdge(Graph graph, int start, int end, int weight) {
        Edge edge = graph.addEdge(start + "-" + end, String.valueOf(start), String.valueOf(end));
        edge.setAttribute("ui.label", weight);
        edge.setAttribute("weight", weight);
        edge.getSourceNode().setAttribute("ui.label", start);
        edge.getTargetNode().setAttribute("ui.label", end);
    }

    public static List<Edge> findKruskalMinimumTree(Graph graph) {
        List<Edge> edges = new ArrayList<>(graph.getEdgeSet());
        edges.sort(Comparator.comparing(e -> e.getAttribute("weight")));

        int[] treeId = new int[graph.getNodeCount()];

        for (int i = 0; i < graph.getNodeCount(); i++) {
            treeId[i] = i;
        }

        List<Edge> treeEdges = new ArrayList<>();

        for (Edge edge : edges) {
            if (treeId[edge.getSourceNode().getIndex()] != treeId[edge.getTargetNode().getIndex()]) {
                treeEdges.add(edge);
                edge.setAttribute("ui.style", "fill-color: red;");
                int oldId = treeId[edge.getTargetNode().getIndex()];
                int newId = treeId[edge.getSourceNode().getIndex()];

                for (int i = 0; i < graph.getNodeCount(); i++) {
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
            sumWeight += edge.getAttribute("weight", Integer.class);
        }

        return sumWeight;
    }

    public static Graph readGraphFromFile(String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));

        Graph graph = new SingleGraph("Graph");
        graph.setStrict(false);
        graph.setAutoCreate(true);

        for (String line : lines) {
            String[] tokens = line.split(" ");
            Integer edgeStart = Integer.parseInt(tokens[0]);
            Integer edgeEnd = Integer.parseInt(tokens[1]);
            Integer edgeWeight = Integer.parseInt(tokens[2]);

            addEdge(graph, edgeStart, edgeEnd, edgeWeight);
        }

        return graph;
    }

}
