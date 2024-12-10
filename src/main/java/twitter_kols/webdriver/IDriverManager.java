package twitter_kols.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class IDriverManager {
    // Khai báo biến WebDriver và WebDriverWait để sử dụng cho các thao tác với trình duyệt.
    protected static WebDriver driver;
    protected static WebDriverWait webDriverWait;

    // Constructor khởi tạo driver và thiết lập các tham số cần thiết.
    public IDriverManager() {
        this.setupDriver();  // Gọi phương thức setupDriver để thiết lập driver.
        this.initDriver();    // Gọi phương thức initDriver để khởi tạo driver.

        // Khởi tạo WebDriverWait với thời gian chờ là 10 giây.
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Phương thức abstract để setup driver. Cần được triển khai trong lớp con.
    abstract protected void setupDriver();

    // Phương thức abstract để khởi tạo driver. Cần được triển khai trong lớp con.
    abstract protected void initDriver();

    // Phương thức trả về driver hiện tại.
    public static WebDriver getDriver() {
        return driver;
    }

    // Phương thức trả về đối tượng WebDriverWait.
    public static WebDriverWait getWebDriverWait() {
        return webDriverWait;
    }

    // Phương thức đóng trình duyệt.
    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
