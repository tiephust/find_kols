package twitter_kols.core.graph;

import org.apache.commons.csv.CSVRecord;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Lớp `GraphBuilder` dùng để xây dựng một đồ thị có hướng từ dữ liệu CSV hoặc JSON.
 */
public class GraphBuilder {

    private final Graph graph;

    /**
     * Constructor nhận đối tượng `Graph` để thực hiện các thao tác xây dựng đồ thị.
     *
     * @param graph Đối tượng đồ thị cần được xây dựng.
     */
    public GraphBuilder(Graph graph) {
        this.graph = graph;
    }

    /**
     * Xây dựng đồ thị từ danh sách các bản ghi CSV.
     * Dữ liệu CSV chứa các cột:
     * - KOL (Key Opinion Leader)
     * - Followers (Danh sách người theo dõi, cách nhau bởi dấu phẩy)
     * - Verified Followers (Người theo dõi được xác thực)
     * - Following (Danh sách người mà KOL đang theo dõi)
     *
     * @param records Danh sách các bản ghi CSV.
     */
    public void buildGraphFromCSV(List<CSVRecord> records) {
        for (CSVRecord record : records) {
            String kol = record.get(0); // Lấy tên KOL
            String followers = record.get(1); // Lấy danh sách Followers
            String verifiedFollowers = record.get(2); // Lấy danh sách Verified Followers
            String following = record.get(3); // Lấy danh sách Following

            // Thêm KOL vào đồ thị
            graph.addNode(kol);

            // Xử lý và thêm Followers vào đồ thị
            String[] followerList = followers.split(",\\s*");
            for (String follower : followerList) {
                graph.addNode(follower);      // Thêm follower vào đồ thị
                graph.addEdge(follower, kol); // Tạo cạnh từ follower đến KOL
            }

            // Xử lý và thêm Verified Followers
            String[] verifiedFollowerList = verifiedFollowers.split(",\\s*");
            for (String verifiedFollower : verifiedFollowerList) {
                graph.addNode(verifiedFollower);      // Thêm verified follower vào đồ thị
                graph.addEdge(verifiedFollower, kol); // Tạo cạnh từ verified follower đến KOL
            }

            // Xử lý và thêm Following vào đồ thị
            String[] followingList = following.split(",\\s*");
            for (String follow : followingList) {
                graph.addNode(follow);     // Thêm node follow vào đồ thị
                graph.addEdge(kol, follow); // Tạo cạnh từ KOL đến follow
            }
        }
    }

    /**
     * Xây dựng đồ thị từ dữ liệu JSON.
     * Dữ liệu JSON chứa các key-value:
     * - Key: Tên KOL
     * - Value: Đối tượng JSON chứa các thông tin `retweetComments`, `tweetComments`, `repostOwner`
     *
     * @param jsonObject Dữ liệu JSON đầu vào.
     */
    public void buildGraphFromJSON(JSONObject jsonObject) {
        for (String kol : jsonObject.keySet()) {
            JSONObject kolData = jsonObject.getJSONObject(kol);

            // Xử lý các bình luận trên bài đăng retweet
            JSONObject retweetComments = kolData.getJSONObject("retweetComments");
            for (String tweetId : retweetComments.keySet()) {
                JSONArray commentorsArray = retweetComments.getJSONArray(tweetId);
                for (int i = 0; i < commentorsArray.length(); i++) {
                    String commentor = commentorsArray.getString(i);
                    graph.addNode(commentor);    // Thêm người bình luận
                    graph.addNode(kol);          // Thêm KOL
                    graph.addEdge(commentor, tweetId); // Tạo cạnh từ người bình luận đến bài đăng
                    graph.addEdge(kol, tweetId);       // Tạo cạnh từ KOL đến bài đăng
                    graph.addEdge(tweetId, kol);       // Tạo cạnh ngược từ bài đăng đến KOL
                }
            }

            // Xử lý các bình luận trên bài đăng thường
            JSONObject tweetComments = kolData.getJSONObject("tweetComments");
            for (String tweetId : tweetComments.keySet()) {
                JSONArray commentorsArray = tweetComments.getJSONArray(tweetId);
                for (int i = 0; i < commentorsArray.length(); i++) {
                    String commentor = commentorsArray.getString(i);
                    graph.addNode(commentor);    // Thêm người bình luận
                    graph.addNode(kol);          // Thêm KOL
                    graph.addEdge(commentor, tweetId); // Tạo cạnh từ người bình luận đến bài đăng
                    graph.addEdge(tweetId, kol);       // Tạo cạnh từ bài đăng đến KOL
                }
            }

            // Xử lý thông tin reposts
            JSONObject repostOwner = kolData.getJSONObject("repostOwner");
            for (String tweetId : repostOwner.keySet()) {
                String repostedBy = repostOwner.getString(tweetId);
                graph.addNode(repostedBy);    // Thêm người repost
                graph.addNode(kol);          // Thêm KOL
                graph.addEdge(repostedBy, tweetId); // Tạo cạnh từ người repost đến bài đăng
                graph.addEdge(kol, tweetId);       // Tạo cạnh từ KOL đến bài đăng
                graph.addEdge(tweetId, kol);       // Tạo cạnh ngược từ bài đăng đến KOL
            }
        }
    }
}
