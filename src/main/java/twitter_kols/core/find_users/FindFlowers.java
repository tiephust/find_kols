package twitter_kols.core.find_users;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import twitter_kols.webdriver.IDriverManager;

import java.util.*;

public class FindFlowers {
    protected WebDriver driver;
    protected WebDriverWait webDriverWait;

    public FindFlowers() {
        this.driver = IDriverManager.getDriver();
        this.webDriverWait = IDriverManager.getWebDriverWait();
    }

    public long findCountFlowers(String kolUrl) {
        driver.get(kolUrl);
        WebElement followersLink = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("a[href*='verified_followers']")
        )).findElement(By.cssSelector("span"));;

        // Lấy nội dung (text) của thẻ a
        String countFollowers = followersLink.getText();

        // Kiểm tra và chuyển đổi giá trị nếu có định dạng M hoặc K
        // Chuyển đổi giá trị thành số

        // Trả về giá trị đã chuyển đổi dưới dạng chuỗi (nếu cần)
        return convertToNumber(countFollowers);
    }
    private long convertToNumber(String input) {
        // Xóa các khoảng trắng dư thừa
        input = input.trim();
        // Kiểm tra hậu tố (M hoặc K)
        if (input.endsWith("M") || input.endsWith("K")) {
            char suffix = input.charAt(input.length() - 1); // Lấy hậu tố
            input = input.substring(0, input.length() - 1).trim(); // Loại bỏ hậu tố

            // Xóa dấu phẩy
            input = input.replace(",", "");
            // Chuyển chuỗi thành số dạng `double`
            double value = Double.parseDouble(input);

            // Nhân với giá trị tương ứng
            if (suffix == 'M') {
                return (long) (value * 1_000_000); // M = triệu
            } else if (suffix == 'K') {
                return (long) (value * 1_000); // K = nghìn
            }
        } else {
            // Trường hợp không có hậu tố, chỉ xóa dấu phẩy và chuyển đổi
            input = input.replace(",", "");
            return Long.parseLong(input);
        }
        return 0; // Trường hợp không hợp lệ
    }


    public int findCountUsers(String kolUrl) {
        int count = 0;
        driver.get(kolUrl);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='cellInnerDiv']")));
        List<WebElement> users = driver.findElements(By.cssSelector("[data-testid='cellInnerDiv']"));
        List<String> usernames = new ArrayList<>();
        for (WebElement user : users) {
            try {
                String href = user.findElement(By.tagName("a")).getAttribute("href");
                String username = href.substring(href.lastIndexOf('/') + 1); // Get everything after the last '/'
                usernames.add(username);
                count++;
            } catch (NoSuchElementException e) {
                System.out.println("Element not found for WebElement user : " + user);
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }

        return count;
    }
}
