package twitter_kols.core.graph;

import java.util.HashMap;
import java.util.Map;

/**
 * Lớp `PageRank` dùng để tính toán giá trị PageRank của các nút trong đồ thị.
 */
public class PageRank {

    private Graph graph;
    private double dampingFactor; // Hệ số giảm dần (default: 0.85)
    private int maxIterations;   // Số lần lặp tối đa
    private double tolerance;    // Ngưỡng hội tụ (độ chênh lệch tối đa giữa các giá trị PageRank qua các vòng lặp)

    /**
     * Constructor mặc định với các giá trị chuẩn cho hệ số giảm dần, số lần lặp và ngưỡng hội tụ.
     *
     * @param graph Đối tượng đồ thị cần tính PageRank.
     */
    public PageRank(Graph graph) {
        this.graph = graph;
        this.dampingFactor = 0.85;
        this.maxIterations = 100; // Default to 100 iterations
        this.tolerance = 1e-6;    // Default tolerance
    }

    /**
     * Constructor cho phép tùy chỉnh các tham số tính toán.
     *
     * @param graph         Đối tượng đồ thị.
     * @param dampingFactor Hệ số giảm dần (thường là 0.85).
     * @param maxIterations Số lần lặp tối đa.
     * @param tolerance     Ngưỡng hội tụ.
     */
    public PageRank(Graph graph, double dampingFactor, int maxIterations, double tolerance) {
        this.graph = graph;
        this.dampingFactor = dampingFactor;
        this.maxIterations = maxIterations;
        this.tolerance = tolerance;
    }

    /**
     * Tính toán giá trị PageRank cho tất cả các nút trong đồ thị.
     *
     * @return Map chứa cặp (node, PageRank value).
     */
    public Map<String, Double> calculatePageRank() {
        Map<String, Double> ranks = new HashMap<>();         // Giá trị PageRank hiện tại
        Map<String, Double> previousRanks = new HashMap<>(); // Giá trị PageRank của vòng lặp trước
        double danglingSum = 0.0;                            // Tổng PageRank của các nút "dangling" (không có outgoing edge)

        // Khởi tạo giá trị PageRank ban đầu
        for (String node : graph.getNodes()) {
            ranks.put(node, 1.0); // Tất cả các nút ban đầu đều có giá trị PageRank là 1
            previousRanks.put(node, 1.0 / graph.getNodeCount());
        }

        // Lặp qua các vòng để cập nhật giá trị PageRank
        for (int i = 0; i < maxIterations; i++) {
            // Tính tổng PageRank của các nút dangling
            danglingSum = 0.0;
            for (String node : graph.getNodes()) {
                if (graph.getOutDegree(node) == 0) { // Nếu nút không có outgoing edge
                    danglingSum += previousRanks.get(node);
                }
            }

            // Cập nhật PageRank cho mỗi nút
            for (String node : graph.getNodes()) {
                double newRank = (1 - dampingFactor) / graph.getNodeCount(); // Phần cơ bản
                double sum = 0.0;

                // Tính tổng các giá trị PageRank từ các nút liên kết đến
                for (String neighbor : graph.getGraph().get(node)) {
                    sum += previousRanks.get(neighbor) / graph.getOutDegree(neighbor);
                }

                // Thêm đóng góp từ các nút dangling và phần damping
                newRank += dampingFactor * sum + dampingFactor * (danglingSum / graph.getNodeCount());
                ranks.put(node, newRank);
            }

            // Kiểm tra hội tụ
            boolean converged = true;
            for (String node : graph.getNodes()) {
                if (Math.abs(ranks.get(node) - previousRanks.get(node)) > tolerance) {
                    converged = false; // Nếu bất kỳ nút nào không hội tụ thì tiếp tục lặp
                }
            }

            if (converged) {
                break; // Nếu tất cả các nút hội tụ thì dừng lặp
            }

            // Cập nhật giá trị PageRank cho vòng lặp tiếp theo
            previousRanks.putAll(ranks);
        }

        return ranks;  // Trả về kết quả cuối cùng
    }

    /**
     * Lấy giá trị PageRank của một người dùng cụ thể.
     *
     * @param user  Tên người dùng.
     * @param ranks Map chứa giá trị PageRank của tất cả các nút.
     * @return Giá trị PageRank của người dùng (0.0 nếu không tồn tại).
     */
    public double getRankOfUser(String user, Map<String, Double> ranks) {
        return ranks.getOrDefault(user, 0.0);
    }
}
