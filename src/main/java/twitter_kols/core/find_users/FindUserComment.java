package twitter_kols.core.find_users;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import twitter_kols.webdriver.IDriverManager;

public class FindUserComment {
    // Driver WebDriver dùng để điều khiển trình duyệt.
    protected  WebDriver driver;
    // WebDriverWait để chờ các điều kiện được
    protected WebDriverWait webDriverWait;

    public FindUserComment() {
        this.driver = IDriverManager.getDriver();
        this.webDriverWait = IDriverManager.getWebDriverWait();
    }

    public void findUserComment() {

    }
}
