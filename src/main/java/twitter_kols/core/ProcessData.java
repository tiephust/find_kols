package twitter_kols.core;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import twitter_kols.core.find_users.FindFlowers;
import twitter_kols.core.find_users.FindKolsUrl;
import twitter_kols.utils.FileWriters;
import twitter_kols.webdriver.IDriverManager;

import java.util.List;
import java.util.Set;

public class ProcessData {
    protected WebDriver driver;
    protected WebDriverWait webDriverWait;
    private FindFlowers findFlowers;

    public ProcessData() {
        this.driver = IDriverManager.getDriver();
        this.webDriverWait = IDriverManager.getWebDriverWait();
        this.findFlowers = new FindFlowers();
    }

    public void processData() {
        FindKolsUrl findKols = new FindKolsUrl();
        findKols.findKols();
        Set<String> listKolsUrl = findKols.getListKoks();

        JSONArray kolDataArray = new JSONArray(); // Lưu trữ tất cả thông tin KOLs

        for (String kolUrl : listKolsUrl) {
            long flowersCount = findFlowers.findCountFlowers(kolUrl);
            String urlList = kolUrl.replace("\"", "").trim();
            String urlListVerifiedFollowers = urlList + "/verified_followers";
            int verifiedFollowersCount = findFlowers.findCountUsers(urlListVerifiedFollowers);

            JSONArray tweetsArray = new JSONArray(); // Lưu trữ thông tin tweet

            List<WebElement> tweets = webDriverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.cssSelector("div[data-testid='cellInnerDiv']")
            ));

            for (WebElement tweet : tweets) {
                System.out.println("Tweet: " + tweet.getText());
                int tweetId = 0;

                // Sử dụng WebDriverWait để chờ các phần tử cần thiết
                String commentCount = webDriverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("button[data-testid='reply'] span.css-146c3p1 r-bcqeeo r-1ttztb7 r-qvutc0 r-37j5jr r-a023e6 r-rjixqe r-16dba41 r-1awozwy r-6koalj r-1h0z5md r-o7ynqc r-clp7b1 r-3s2u2q"))).get(tweetId).getText();

                String retweetCount = webDriverWait.until(ExpectedConditions.visibilityOf(
                        tweet.findElement(By.cssSelector("button[data-testid='retweet'] span.css-146c3p1 r-bcqeeo r-1ttztb7 r-qvutc0 r-37j5jr r-a023e6 r-rjixqe r-16dba41 r-1awozwy r-6koalj r-1h0z5md r-o7ynqc r-clp7b1 r-3s2u2q"))
                )).getText();

                String likeCount = webDriverWait.until(ExpectedConditions.visibilityOf(
                        tweet.findElement(By.cssSelector("button[data-testid='like'] span.css-146c3p1 r-bcqeeo r-1ttztb7 r-qvutc0 r-37j5jr r-a023e6 r-rjixqe r-16dba41 r-1awozwy r-6koalj r-1h0z5md r-o7ynqc r-clp7b1 r-3s2u2q"))
                )).getText();

                String viewCount = webDriverWait.until(ExpectedConditions.visibilityOf(
                        tweet.findElement(By.cssSelector("a[aria-label*='views. View post analytics'] span.css-146c3p1 r-bcqeeo r-1ttztb7 r-qvutc0 r-37j5jr r-a023e6 r-rjixqe r-16dba41 r-1awozwy r-6koalj r-1h0z5md r-o7ynqc r-clp7b1 r-3s2u2q"))
                )).getText();

                // Tạo JSONObject cho mỗi tweet
                JSONObject tweetData = new JSONObject();
                tweetData.put("tweetId", String.valueOf(tweetId));
                tweetData.put("commentCount", commentCount);
                tweetData.put("retweetCount", retweetCount);
                tweetData.put("likeCount", likeCount);
                tweetData.put("viewCount", viewCount);

                tweetsArray.put(tweetData); // Thêm vào mảng tweet
                tweetId++;
            }

            // Tạo JSONObject cho KOL
            JSONObject kolData = new JSONObject();
            kolData.put("kolUrl", kolUrl);
            kolData.put("flowersCount", flowersCount);
            kolData.put("verifiedFollowersCount", verifiedFollowersCount);
            kolData.put("tweets", tweetsArray);

            kolDataArray.put(kolData); // Thêm vào mảng KOLs
        }

        // Ghi dữ liệu vào file JSON
        FileWriters.writeToJSONFile("result.json", kolDataArray);
    }
}
