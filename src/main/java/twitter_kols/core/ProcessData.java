package twitter_kols.core;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import twitter_kols.core.find_users.FindFlowers;
import twitter_kols.core.find_users.FindKolsUrl;
import twitter_kols.utils.csv.FileWriters;
import twitter_kols.webdriver.AbstractDriverManager;
import twitter_kols.webdriver.ChromeDriverManager;
import org.openqa.selenium.By;

import java.util.List;
import java.util.Set;

public class ProcessData {
    private final ChromeDriverManager driverManager;
    private final FindFlowers findFlowers;

    public ProcessData() {
        this.driverManager = new ChromeDriverManager();
        this.findFlowers = new FindFlowers();
    }

    public void processData() {
        FindKolsUrl findKols = new FindKolsUrl();
        findKols.findKols();
        Set<String> listKolsUrl = findKols.getListKoks();

        JSONArray kolDataArray = new JSONArray(); // Lưu trữ tất cả thông tin KOLs

        for (String kolUrl : listKolsUrl) {
            long flowersCount = findFlowers.findCountFlowers(kolUrl);

            JSONArray tweetsArray = new JSONArray(); // Lưu trữ thông tin tweet
            System.out.println(kolUrl);
            List<WebElement> tweets = null;
            String linkTweet = ".css-175oi2r.r-16y2uox.r-1wbh5a2.r-1ny4l3l"; // Initial link
            String linkInteract = ".css-175oi2r.r-1kbdv8c.r-18u37iz.r-1wtj0ep.r-1ye8kvj.r-1s2bzr4"; // Interaction link

            // Kiểm tra nếu không tìm thấy tweet, thay đổi linkTweet
            try{
                tweets = AbstractDriverManager.getWebDriverWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(linkTweet)));

            } catch (Exception e){
                linkTweet = ".css-175oi2r r-eqz5dr r-16y2uox r-1wbh5a2"; // New link if not found
                linkInteract = ".css-175oi2r r-1kbdv8c r-18u37iz r-1wtj0ep r-1ye8kvj r-1s2bzr4"; // Interaction link stays the same
                tweets = AbstractDriverManager.getWebDriverWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(linkTweet)));
            }
            if (tweets != null && !tweets.isEmpty()) {
                for (WebElement tweet : tweets) {
                    processTweetData(tweets, tweetsArray, linkInteract);
                    System.out.println("Tweet: " + tweet.getText());
                }
            } else {
                System.out.println("Không tìm thấy tweet.");
            }

            String urlList = kolUrl.replace("\"", "").trim();
            String urlListVerifiedFollowers = urlList + "/verified_followers";
            int verifiedFollowersCount = findFlowers.findCountUsers(urlListVerifiedFollowers);
            // Tạo JSONObject cho KOL
            JSONObject kolData = new JSONObject();
            kolData.put("kolUrl", kolUrl);
            kolData.put("flowersCount", flowersCount);
            kolData.put("verifiedFollowersCount", verifiedFollowersCount);
            kolData.put("tweets", tweetsArray);

            kolDataArray.put(kolData); // Thêm vào mảng KOLs
            FileWriters.writeToJSONFile("result.json", kolData);
        }

        // Ghi dữ liệu vào file JSON
//        FileWriters.writeToJSONFile("result.json", kolDataArray);
    }

    public void processTweetData(List<WebElement> tweets, JSONArray tweetsArray, String linkInteract) {
        int tweetId = 0;
        for (WebElement tweet : tweets) {
            System.out.println("Tweet: " + tweet.getText());
            WebElement interactElement = driverManager.visibilityOfElementLocated(linkInteract);
            String ariaLabelText = interactElement.getAttribute("aria-label");
            System.out.println("aria-label: " + ariaLabelText);
            int commentCount = 0, retweetCount = 0, likeCount = 0, viewCount = 0, bookmarkCount = -1;

            String[] parts = ariaLabelText.split(", ");

            try {
                if (parts.length > 0) {
                    commentCount = Integer.parseInt(parts[0].split(" ")[0]);
                }
            } catch (Exception ignored) {
            }

            try {
                if (parts.length > 1) {
                    retweetCount = Integer.parseInt(parts[1].split(" ")[0]);
                }
            } catch (Exception ignored) {
            }

            try {
                if (parts.length > 2) {
                    likeCount = Integer.parseInt(parts[2].split(" ")[0]);
                }
            } catch (Exception ignored) {}

            try {
                if (parts.length > 3) {
                    viewCount = Integer.parseInt(parts[3].split(" ")[0]);
                }
            } catch (Exception ignored) {
            }

            try {
                if (parts.length > 4) {
                    bookmarkCount = Integer.parseInt(parts[4].split(" ")[0]);
                }
            } catch (Exception ignored) {
            }

            // Tạo JSONObject cho mỗi tweet
            JSONObject tweetData = new JSONObject();
            tweetData.put("tweetId", tweetId);
            tweetData.put("commentCount", commentCount);
            tweetData.put("retweetCount", retweetCount);
            tweetData.put("likeCount", likeCount);
            tweetData.put("viewCount", viewCount);
            tweetData.put("bookmarkCount", bookmarkCount);

            tweetsArray.put(tweetData); // Thêm vào mảng tweet
            tweetId++;
        }
    }
}
