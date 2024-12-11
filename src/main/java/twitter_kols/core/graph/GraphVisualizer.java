package twitter_kols.core.graph;

import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Lớp `GraphVisualizer` dùng để hiển thị đồ thị bằng cách sử dụng thư viện JGraphX.
 */
public class GraphVisualizer {
    private final Map<String, Set<String>> graph;

    /**
     * Constructor nhận đối tượng `Graph` để lấy dữ liệu đồ thị cần hiển thị.
     *
     * @param graphManager Đối tượng `Graph` chứa đồ thị đã được xây dựng.
     */
    public GraphVisualizer(Graph graphManager) {
        this.graph = graphManager.getGraph();
    }

    /**
     * Phương thức chính để hiển thị đồ thị.
     * Sử dụng JGraphX để tạo các đỉnh (vertex) và các cạnh (edge),
     * sau đó sắp xếp chúng bằng thuật toán `mxFastOrganicLayout`.
     */
    public void visualize() {
        // Tạo một đối tượng mxGraph để biểu diễn đồ thị
        mxGraph mxGraph = new mxGraph();
        Object parent = mxGraph.getDefaultParent();

        // Bắt đầu cập nhật mô hình đồ thị
        mxGraph.getModel().beginUpdate();
        try {
            // Map để lưu các đỉnh (vertex) theo tên của chúng
            Map<String, Object> vertexMap = new HashMap<>();

            // Thêm các đỉnh (node) vào đồ thị
            for (String node : graph.keySet()) {
                // Mỗi đỉnh có kích thước 80x30 và được đặt tên là `node`
                Object vertex = mxGraph.insertVertex(parent, null, node, 0, 0, 80, 30);
                vertexMap.put(node, vertex);
            }

            // Thêm các cạnh (edge) giữa các đỉnh
            for (String node : graph.keySet()) {
                for (String connectedNode : graph.get(node)) {
                    // Tạo cạnh từ `node` đến `connectedNode`
                    mxGraph.insertEdge(parent, null, "", vertexMap.get(node), vertexMap.get(connectedNode));
                }
            }
        } finally {
            // Kết thúc cập nhật mô hình đồ thị
            mxGraph.getModel().endUpdate();
        }

        // Sử dụng thuật toán `mxFastOrganicLayout` để sắp xếp đồ thị
        mxFastOrganicLayout layout = new mxFastOrganicLayout(mxGraph);
        layout.execute(mxGraph.getDefaultParent());

        // Tạo cửa sổ JFrame để hiển thị đồ thị
        JFrame frame = new JFrame("Graph Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Thêm đồ thị vào JFrame thông qua `mxGraphComponent`
        frame.add(new mxGraphComponent(mxGraph));

        // Thiết lập kích thước và hiển thị cửa sổ
        frame.setSize(1200, 900);
        frame.setVisible(true);
    }
}
