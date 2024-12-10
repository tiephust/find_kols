package twitter_kols.core.find_users;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import twitter_kols.conditions.TwitterLoggedIn;
import twitter_kols.propertices.Properties;
import twitter_kols.webdriver.ChromeDriverManager;
import twitter_kols.webdriver.IDriverManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FindKols {
    // Driver WebDriver dùng để điều khiển trình duyệt.
    protected WebDriver driver;
    // WebDriverWait để chờ các điều kiện được
    protected WebDriverWait webDriverWait;
    // Kiem tra dieu kien "da dang nhap Twitter".
    private final TwitterLoggedIn twitterLoggedIn = new TwitterLoggedIn();
    // Từ khóa tìm kiếm KOLs.
    private final String keyword = Properties.KEYWORD.val();

    private  List<String> listKoks;

    public FindKols() {
        this.driver = IDriverManager.getDriver();
        this.webDriverWait = IDriverManager.getWebDriverWait();
    }

    public void findKols() {
        System.out.println("Finding KOLs...");
        if(!twitterLoggedIn.apply(driver)) {
            ChromeDriverManager chromeDriverManager = new ChromeDriverManager();
            chromeDriverManager.initDriver();
        }
        try {
            listKoks = ListKolsFromKeyWord(keyword);
            System.out.println("List of KOLs: " + listKoks);
        } catch (Exception e) {
            System.err.println("Error during scraping for keyword: " + keyword);
            e.printStackTrace();
        }
    }

    public List<String> ListKolsFromKeyWord(String keyword) {
        System.out.println("Scraping KOLs...");
        String hashtag = "#" + keyword;
        WebElement searchField = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[placeholder='Search']")));
        searchField.sendKeys(hashtag);
        searchField.submit();

        WebElement people = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/search?q=%23" + keyword + "&src=typed_query&f=user']")));  // Tìm và chọn bộ lọc 'People'
        people.click();

        Set<String> userLinks = new HashSet<>();
        List<WebElement> users = driver.findElements(By.cssSelector("[data-testid='UserCell'][role='button']"));  // Tìm tất cả các phần tử người dùng

        for (WebElement user : users) {  // Lặp qua danh sách người dùng
            String href = user.findElement(By.tagName("a")).getAttribute("href");  // Lấy liên kết người dùng
            userLinks.add(href);  // Thêm liên kết vào Set
            System.out.println(href);  // In liên kết ra màn hình
        }
        return userLinks.stream().toList();
    }

    public List<String> getListKoks(){
        return listKoks;
    }
}
