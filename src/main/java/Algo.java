import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Algo {

    public static void main(String[] args) throws IOException {
        List<Edge> edges = readGraphFromFile("src/main/resources/edges.txt");
        List<Edge> tree = findKruskalMinimumTree(edges);
        int weight = calculateWeight(tree);

        System.out.println("Kruskal Minimum Tree Weight: " + weight);
    }

    public static List<Edge> findKruskalMinimumTree(List<Edge> edges) {
        edges.sort(Comparator.comparing(Edge::getWeight));

        List<Edge> treeEdges = new ArrayList<>();
        Set<Integer> treeNodes = new HashSet<>();

        for (Edge edge : edges) {
            if (!willCreateCycle(treeNodes, edge)) {
                treeEdges.add(edge);
                treeNodes.add(edge.getStart());
                treeNodes.add(edge.getEnd());
            }
        }

        return treeEdges;
    }

    public static boolean willCreateCycle(Set<Integer> treeNodes, Edge edge) {
        return treeNodes.contains(edge.getStart()) && treeNodes.contains(edge.getEnd());
    }

    public static int calculateWeight(List<Edge> edges) {
        int sumWeight = 0;

        for (Edge edge : edges) {
            sumWeight += edge.getWeight();
        }

        return sumWeight;
    }

    public static List<Edge> readGraphFromFile(String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));
        List<Edge> edges = new ArrayList<>();

        for (String line : lines) {
            String[] tokens = line.split(" ");
            Integer edgeStart = Integer.parseInt(tokens[0]);
            Integer edgeEnd = Integer.parseInt(tokens[1]);
            Integer edgeWeight = Integer.parseInt(tokens[2]);

            edges.add(new Edge(edgeStart, edgeEnd, edgeWeight));
        }

        return edges;
    }

}
