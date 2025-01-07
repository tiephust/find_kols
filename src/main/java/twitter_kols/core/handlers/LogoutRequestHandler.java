package twitter_kols.core.handlers;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import twitter_kols.propertices.Properties;
import twitter_kols.webdriver.AbstractDriverManager;

public class LogoutRequestHandler  {
    // URL của trang đăng xuất Twitter.
    private final String TWITTER_LOGOUT_URL = Properties.LOGOUT_URL.val();

    // Driver WebDriver dùng để điều khiển trình duyệt.
    protected WebDriver driver;

    public LogoutRequestHandler() {
        this.driver = AbstractDriverManager.getDriver();
    }

    public void handleRequest() {
       System.out.println("Logout request executed"); // Ghi log khi bắt đầu xử lý đăng xuất.

        logout(); // Thực hiện đăng xuất.
    }

    private void logout() {
        // Điều hướng đến trang đăng xuất Twitter.
        driver.get(TWITTER_LOGOUT_URL);

        // XPath của nút xác nhận đăng xuất.
        By logoutConfirmBtn = By.xpath("//div[@data-testid=\"confirmationSheetConfirm\"]");

        try {
            // Tạm dừng 3 giây để đảm bảo trang đăng xuất được tải đầy đủ.
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {
        }

        try {
            // Kiểm tra nếu URL hiện tại không phải URL đăng xuất, nghĩa là đã đăng xuất.
            if (!driver.getCurrentUrl().equals(TWITTER_LOGOUT_URL)) {
                System.out.println("Already logged out of twitter"); // Ghi log nếu đã đăng xuất.
                return;
            }
        } catch (TimeoutException e) {
            // Ghi log nếu xảy ra lỗi Timeout và đã đăng xuất.
            System.out.println("Already logged out of twitter");
            return;
        }

        // Tìm phần tử nút xác nhận đăng xuất và nhấn vào nó.
        WebElement logoutConfirmElement = driver.findElement(logoutConfirmBtn);
        logoutConfirmElement.click();

        System.out.println("Logged out of twitter"); // Ghi log khi đăng xuất thành công.
    }
}
