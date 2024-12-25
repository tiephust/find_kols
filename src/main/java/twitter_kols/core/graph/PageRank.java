package twitter_kols.core.graph;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class PageRank {

    public PageRank() {
    }

    public Map<String, Double> calculatePageRank(JSONArray jsonArray) {
        Map<String, Double> pageRankMap = new HashMap<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            double score = 0;
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String kolUrl = jsonObject.getString("kolUrl");
            int flowersCount = jsonObject.getInt("flowersCount");
            int verifiedFollowersCount = jsonObject.getInt("verifiedFollowersCount");
            double tweetScore = calculateTweetScore(jsonObject);
            score += flowersCount + verifiedFollowersCount + tweetScore;
            pageRankMap.put(kolUrl, score);
        }

        return pageRankMap;
    }

    private double calculateTweetScore(JSONObject jsonObject) {
        double tweetScore = 0;
        JSONArray tweets = jsonObject.getJSONArray("tweets");
        int likeCount = Integer.parseInt(tweets.getJSONObject(0).getString("likeCount"));
        int commentCount = Integer.parseInt(tweets.getJSONObject(0).getString("commentCount"));
        int retweetCount = Integer.parseInt(tweets.getJSONObject(0).getString("retweetCount"));
        int viewCount = Integer.parseInt(tweets.getJSONObject(0).getString("viewCount"));
        tweetScore += likeCount + commentCount + retweetCount + viewCount;
        return tweetScore;
    }

    public void printSortedPageRank(Map<String, Double> pageRankMap) {
        pageRankMap.entrySet().stream()
                .sorted((entry1, entry2) -> Double.compare(entry2.getValue(), entry1.getValue()))
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }
}
