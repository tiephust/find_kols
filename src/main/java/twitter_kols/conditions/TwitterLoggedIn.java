package twitter_kols.conditions;

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
    @Override
    public Boolean apply(WebDriver driver) {
        List<WebElement> menu = Objects.requireNonNull(driver).findElements(By.xpath("//div[@data-testid=\"SideNav_AccountSwitcher_Button\"]"));
        return !menu.isEmpty();
    }
}
