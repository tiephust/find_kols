package twitter_kols.core.find_users;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import twitter_kols.webdriver.IDriverManager;

import java.util.*;

public class FindFlowers {
    // Driver WebDriver dùng để điều khiển trình duyệt.
    protected WebDriver driver;
    // WebDriverWait để chờ các điều kiện được
    protected WebDriverWait webDriverWait;

    public FindFlowers() {
        this.driver = IDriverManager.getDriver();
        this.webDriverWait = IDriverManager.getWebDriverWait();
    }

    public List<String> findListUsers(String kolUrl) {
        driver.get(kolUrl);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='cellInnerDiv']")));
        List<WebElement> users = driver.findElements(By.cssSelector("[data-testid='cellInnerDiv']"));
        List<String> usernames = new ArrayList<>();
        for (WebElement user : users) {
            try {
                String href = user.findElement(By.tagName("a")).getAttribute("href");
                String username = href.substring(href.lastIndexOf('/') + 1); // Get everything after the last '/'
                usernames.add(username);
            } catch (NoSuchElementException e) {
                System.out.println("Element not found for WebElement user : " + user);
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }

        return usernames;
    }
}
