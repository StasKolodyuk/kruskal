import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Graph {

    private List<Edge> edges;

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }


    public int getNodesCount() {
        Set<Integer> nodes = new HashSet<>();

        for (Edge edge : edges) {
            nodes.add(edge.getStart());
            nodes.add(edge.getEnd());
        }

        return nodes.size();
    }

}
