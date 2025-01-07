package twitter_kols.core.graph;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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
        int likeCount = tweets.getJSONObject(0).getInt("likeCount");
        int commentCount = tweets.getJSONObject(0).getInt("commentCount");
        int retweetCount = tweets.getJSONObject(0).getInt("retweetCount");
        int viewCount = tweets.getJSONObject(0).getInt("viewCount");
        tweetScore += likeCount + commentCount + retweetCount + viewCount;
        return tweetScore;
    }

    public void printSortedPageRank(Map<String, Double> pageRankMap) {
        System.out.println("PageRank:");
        AtomicInteger i = new AtomicInteger(1);  // Khởi tạo giá trị i bắt đầu từ 1
        pageRankMap.entrySet().stream()
                .sorted((entry1, entry2) -> Double.compare(entry2.getValue(), entry1.getValue()))
                .forEach(entry -> System.out.println(i.getAndIncrement() + ": " + entry.getKey() + ": " + entry.getValue()));
    }
}
