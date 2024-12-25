package twitter_kols.core.handlers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import twitter_kols.conditions.TwitterLoggedIn;
import twitter_kols.propertices.Properties;
import twitter_kols.webdriver.IDriverManager;

public class LoginRequestHandler{
    // Driver WebDriver dùng để điều khiển trình duyệt.
    protected  WebDriver driver;
    // WebDriverWait để chờ các điều kiện được
    protected WebDriverWait webDriverWait;

    // URL trang đăng nhập Twitter.
    private static final String TWITTER_LOGIN_URL = Properties.LOGIN_URL.val();

    // Kiểm tra điều kiện "đã đăng nhập Twitter".
    private static final TwitterLoggedIn twitterLoggedIn = new TwitterLoggedIn();

    public LoginRequestHandler() {
        this.driver = IDriverManager.getDriver();
        this.webDriverWait = IDriverManager.getWebDriverWait();
    }

    public void handleRequest() {
        System.out.println("Login request executed"); // Ghi log khi bắt đầu xử lý đăng nhập.

        login(); // Thực hiện đăng nhập.
    }

    public void login() {
        // Điều hướng đến trang đăng nhập Twitter.
        driver.get(TWITTER_LOGIN_URL);

        // Kiểm tra xem đã đăng nhập hay chưa.
        if (checkIfLoggedIn()) {
            System.out.println("Already logged in to twitter"); // Ghi log nếu đã đăng nhập.
            return;
        }

        // Enter username
        WebElement usernameField = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[name='text']")));
        usernameField.sendKeys(Properties.USERNAME.val());

        // Click Next button
        WebElement nextButton = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[role='button'][type='button'][class=\"css-175oi2r r-sdzlij r-1phboty r-rs99b7 r-lrvibr r-ywje51 r-184id4b r-13qz1uu r-2yi16 r-1qi8awa r-3pj75a r-1loqt21 r-o7ynqc r-6416eg r-1ny4l3l\"]")));
        nextButton.click();

        /*
        Nếu bi xác thực email thì bỏ comment dòng này
        */
        WebElement usernameField1 = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[name='text']")));
        usernameField1.sendKeys(Properties.EMAIL.val());
        /*
        Nếu bi xác thực email thì bỏ comment dòng này
        */
        WebElement nextButton1 = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[role='button'][type='button'][class=\"css-175oi2r r-sdzlij r-1phboty r-rs99b7 r-lrvibr r-19yznuf r-64el8z r-1fkl15p r-1loqt21 r-o7ynqc r-6416eg r-1ny4l3l\"]")));
        nextButton1.click();

        // Enter password
        WebElement passwordField = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[name=\"password\"][spellcheck=\"true\"]")));
        passwordField.sendKeys(Properties.PASSWORD.val());

        // Click Login button
        WebElement loginButton = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-testid=\"LoginForm_Login_Button\"][type=\"button\"]")));
        loginButton.click();

        // Cho den khi dang nhap thanh cong
//        webDriverWait.until(twitterLoggedIn);

        System.out.println("Logged into twitter"); // Ghi log khi đăng nhập thành công.
    }


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
