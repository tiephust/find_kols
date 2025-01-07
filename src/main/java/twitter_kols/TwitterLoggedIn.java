package twitter_kols;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;
import java.util.Objects;

/**
 * Lớp kiểm tra xem người dùng đã đăng nhập vào Twitter hay chưa.
 */
public class TwitterLoggedIn implements ExpectedCondition<Boolean> {

    /**
     * Phương thức được gọi để kiểm tra điều kiện cụ thể (người dùng đã đăng nhập).
     * @param driver Đối tượng WebDriver để điều khiển trình duyệt.
     * @return True nếu tìm thấy menu điều hướng tài khoản, False nếu không.
     */
    @Override
    public Boolean apply(WebDriver driver) {
        // Tìm tất cả các phần tử trong trang có thuộc tính data-testid là "SideNav_AccountSwitcher_Button".
        List<WebElement> menu = Objects.requireNonNull(driver).findElements(By.xpath("//div[@data-testid=\"SideNav_AccountSwitcher_Button\"]"));

        // Trả về True nếu danh sách menu không rỗng (tức là phần tử đã được tìm thấy).
        return !menu.isEmpty();
    }
}
