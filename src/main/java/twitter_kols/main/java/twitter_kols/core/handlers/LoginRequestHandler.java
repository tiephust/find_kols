package twitter_kols.main.java.twitter_kols.core.handlers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import twitter_kols.conditions.TwitterLoggedIn;
import twitter_kols.webdriver.IDriverManager;
import twitter_kols.propertices.Properties;

public class LoginRequestHandler{
    // Driver WebDriver dùng để điều khiển trình duyệt.
    protected static final WebDriver driver = IDriverManager.getDriver();
    // FluentWait để chờ các điều kiện được thỏa mãn.
    protected final Wait<WebDriver> fluentWait = IDriverManager.getFluentWait();
    // WebDriverWait để chờ các điều kiện được
    protected final WebDriverWait webDriverWait = IDriverManager.getWebDriverWait();

    // URL trang đăng nhập Twitter.
    private static final String TWITTER_LOGIN_URL =Properties.LOGIN_URL.val();

    // Kiểm tra điều kiện "đã đăng nhập Twitter".
    private static final TwitterLoggedIn twitterLoggedIn = new TwitterLoggedIn();

    /**
     * Xử lý yêu cầu đăng nhập.
     */
    public void handleRequest() {
        System.out.println("Login request executed"); // Ghi log khi bắt đầu xử lý đăng nhập.

        login(); // Thực hiện đăng nhập.
    }

    /**
     * Thực hiện đăng nhập vào Twitter.
     */
    public void login() {
        // Điều hướng đến trang đăng nhập Twitter.
        driver.get(TWITTER_LOGIN_URL);

        // Kiểm tra xem đã đăng nhập hay chưa.
//        if (checkIfLoggedIn()) {
//            System.out.println("Already logged in to twitter"); // Ghi log nếu đã đăng nhập.
//            return;
//        }

        // Đợi cho đến khi trường nhập tài khoản xuất hiện.
//        fluentWait.until(ExpectedConditions.textToBePresentInElementLocated(
//                By.xpath("//span"), "Sign in to X"));

        // Enter username
        WebElement usernameField = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[name='text']")));
        usernameField.sendKeys(Properties.USERNAME.val());

        // Click Next button
        WebElement nextButton = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[role='button'][type='button'][class=\"css-175oi2r r-sdzlij r-1phboty r-rs99b7 r-lrvibr r-ywje51 r-184id4b r-13qz1uu r-2yi16 r-1qi8awa r-3pj75a r-1loqt21 r-o7ynqc r-6416eg r-1ny4l3l\"]")));
        nextButton.click();

        // Enter password
        WebElement passwordField = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[name=\"password\"][spellcheck=\"true\"]")));
        passwordField.sendKeys(Properties.PASSWORD.val());

        // Click Login button
        WebElement loginButton = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid=\"LoginForm_Login_Button\"][type=\"button\"]")));
        loginButton.click();

        // Đợi cho đến khi xác nhận đã đăng nhập thành công.
//        fluentWait.until(twitterLoggedIn);

        System.out.println("Logged into twitter"); // Ghi log khi đăng nhập thành công.
    }

    /**
     * Kiểm tra xem người dùng đã đăng nhập hay chưa.
     * @return True nếu đã đăng nhập, False nếu chưa.
     */
    private boolean checkIfLoggedIn() {
        try {
            // Tạm dừng trong 2 giây để đảm bảo URL được tải xong.
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {
        }

        // Kiểm tra URL hiện tại. Nếu không phải URL đăng nhập, nghĩa là đã đăng nhập.
        return !driver.getCurrentUrl().equals(TWITTER_LOGIN_URL);
    }
}
