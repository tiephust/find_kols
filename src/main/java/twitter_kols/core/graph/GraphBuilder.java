package twitter_kols.core.graph;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Lớp `GraphBuilder` dùng để xây dựng một đồ thị có hướng từ dữ liệu CSV hoặc JSON.
 */
public class GraphBuilder {
    private final Graph graph;

    public GraphBuilder(Graph graph) {
        this.graph = graph;
    }

    public void buildGraphFromJSON(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject kolData = jsonArray.getJSONObject(i);

            String kolUrl = kolData.getString("kolUrl");
            graph.addNode(kolUrl); // Add KOL as a node

            // Add KOL's followers and flowers count as attributes (for PageRank calculation)
            int flowersCount = kolData.getInt("flowersCount");
            int verifiedFollowersCount = kolData.getInt("verifiedFollowersCount");

            // Add tweets to the graph
            JSONArray tweets = kolData.getJSONArray("tweets");
            for (int j = 0; j < tweets.length(); j++) {
                JSONObject tweet = tweets.getJSONObject(j);
                String tweetId = tweet.getString("tweetId");

                // Connect tweet to KOL
                graph.addEdge(kolUrl, tweetId);
                graph.addEdge(tweetId, kolUrl); // Also connect in reverse direction for PageRank

                // Add tweet data to the graph (nodes are tweets themselves)
                graph.addNode(tweetId);

                graph.addEdge(tweetId, "commentCount:" + tweet.getString("commentCount"));
                graph.addEdge(tweetId, "retweetCount:" + tweet.getString("retweetCount"));
                graph.addEdge(tweetId, "likeCount:" + tweet.getString("likeCount"));
                graph.addEdge(tweetId, "viewCount:" + tweet.getString("viewCount"));
            }
        }
    }
}
