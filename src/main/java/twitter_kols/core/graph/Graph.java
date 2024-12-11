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

    // Biến này lưu trữ danh sách kề, mỗi node ánh xạ đến tập các node liền kề
    private final Map<String, Set<String>> graph;

    /**
     * Constructor khởi tạo đồ thị rỗng.
     */
    public Graph() {
        graph = new HashMap<>();
    }

    /**
     * Phương thức thêm một node vào đồ thị.
     * Nếu node đã tồn tại, nó sẽ không bị thêm lại.
     *
     * @param node Tên của node cần thêm.
     */
    public void addNode(String node) {
        graph.putIfAbsent(node, new HashSet<>());
    }

    /**
     * Phương thức thêm một cạnh có hướng giữa hai node.
     * Nếu các node chưa tồn tại, chúng sẽ được tự động thêm vào đồ thị.
     *
     * @param fromNode Node nguồn.
     * @param toNode Node đích.
     */
    public void addEdge(String fromNode, String toNode) {
        graph.putIfAbsent(fromNode, new HashSet<>()); // Thêm node nguồn nếu chưa tồn tại
        graph.putIfAbsent(toNode, new HashSet<>());   // Thêm node đích nếu chưa tồn tại
        graph.get(fromNode).add(toNode);             // Thêm cạnh từ node nguồn đến node đích
    }

    /**
     * Phương thức trả về tập hợp tất cả các node trong đồ thị.
     *
     * @return Tập hợp các node.
     */
    public Set<String> getNodes() {
        return graph.keySet();
    }

    /**
     * Phương thức trả về tổng số lượng node trong đồ thị.
     *
     * @return Số lượng node trong đồ thị.
     */
    public int getNodeCount() {
        return graph.size();
    }

    /**
     * Phương thức trả về bậc ra (out-degree) của một node.
     * Bậc ra là số lượng cạnh xuất phát từ node đó.
     *
     * @param node Node cần tính bậc ra.
     * @return Bậc ra của node. Nếu node không tồn tại, trả về 0.
     */
    public int getOutDegree(String node) {
        return graph.containsKey(node) ? graph.get(node).size() : 0;
    }

    /**
     * Phương thức trả về danh sách kề của đồ thị.
     * Mỗi node được ánh xạ đến tập các node liền kề của nó.
     *
     * @return Bản đồ đại diện danh sách kề của đồ thị.
     */
    public Map<String, Set<String>> getGraph() {
        return graph;
    }
}
