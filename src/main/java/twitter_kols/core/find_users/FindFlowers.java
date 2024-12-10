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

    private Map<String, List<String>> mapKolsFlowers;
    public FindFlowers() {
        this.driver = IDriverManager.getDriver();
        this.webDriverWait = IDriverManager.getWebDriverWait();
    }

    public void findMapKolsFlowers(List<String> listKols) {
        for (String kol : listKols) {
            List<String> flowers = findFlowers(kol);
            mapKolsFlowers.put(kol, flowers);
        }
        System.out.println("Map of KOLs and their flowers: " + mapKolsFlowers);
    }

    public List<String> findFlowers(String kol) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='cellInnerDiv']")));

        List<WebElement> followers = driver.findElements(By.cssSelector("[data-testid='cellInnerDiv']"));
        System.out.println("Total followers : " + followers.size());

        List<String> listFlower = new ArrayList<>();

        for (int i = 0; i < followers.size(); i++) {
            WebElement user = followers.get(i);

            try {
                String href = user.findElement(By.tagName("a")).getAttribute("href");
                String username = href.substring(href.lastIndexOf('/') + 1); // Get everything after the last '/'
                listFlower.add(username);
            } catch (NoSuchElementException e) {
                System.out.println("Element not found for user at index: " + i);
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
        return listFlower;
    }

    public Map<String, List<String>> getMapKolsFlowers() {
        return mapKolsFlowers;
    }
}
