package twitter_kols.core.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Lớp này đại diện cho một đồ thị có hướng sử dụng danh sách kề.
 * Mỗi node là một chuỗi (String), và mỗi cạnh là một mối quan hệ giữa các node.
 */
public class Graph {
    private final Map<String, Set<String>> graph;

    public Graph() {
        graph = new HashMap<>();
    }

    public void addNode(String node) {
        graph.putIfAbsent(node, new HashSet<>());
    }

    public void addEdge(String fromNode, String toNode) {
        graph.putIfAbsent(fromNode, new HashSet<>());
        graph.putIfAbsent(toNode, new HashSet<>());
        graph.get(fromNode).add(toNode);
    }

    public Set<String> getNodes() {
        return graph.keySet();
    }

    public int getNodeCount() {
        return graph.size();
    }

    public int getOutDegree(String node) {
        return graph.containsKey(node) ? graph.get(node).size() : 0;
    }

    public Map<String, Set<String>> getGraph() {
        return graph;
    }
}

