package twitter_kols.core.handlers;

import org.openqa.selenium.WebElement;

import twitter_kols.propertices.Properties;
import twitter_kols.webdriver.ChromeDriverManager;
import twitter_kols.webdriver.AbstractDriverManager;

public class LoginRequestHandler{
    private ChromeDriverManager driverManager;

    // URL trang đăng nhập Twitter.
    private static final String TWITTER_LOGIN_URL = Properties.LOGIN_URL.val();

    public LoginRequestHandler() {
        this.driverManager = new ChromeDriverManager();
    }

    public void handleRequest() {
        System.out.println("Login request executed");
        login();
    }

    public void login() {
        AbstractDriverManager.getDriver().get(TWITTER_LOGIN_URL);

        if (!checkIfLoggedIn()) {
            System.out.println("Already logged in to twitter"); // Ghi log nếu đã đăng nhập.
            return;
        }
        System.out.println("Enter your username");
        // Enter username
        WebElement usernameField = driverManager.visibilityOfElementLocated("[name='text']");
        usernameField.sendKeys(Properties.USERNAME.val());

        // Click Next button
        WebElement nextButton = driverManager.visibilityOfElementLocated("[role='button'][type='button'][class=\"css-175oi2r r-sdzlij r-1phboty r-rs99b7 r-lrvibr r-ywje51 r-184id4b r-13qz1uu r-2yi16 r-1qi8awa r-3pj75a r-1loqt21 r-o7ynqc r-6416eg r-1ny4l3l\"]");
        nextButton.click();

        /*
        Nếu bi xác thực email thì bỏ comment dòng này
        */
//        WebElement usernameField1 = driverManager.visibilityOfElementLocated("[name='text']");
//        usernameField1.sendKeys(Properties.EMAIL.val());
        /*
        Nếu bi xác thực email thì bỏ comment dòng này
        */
//        WebElement nextButton1 = driverManager.elementToBeClickable("[role='button'][type='button'][class=\"css-175oi2r r-sdzlij r-1phboty r-rs99b7 r-lrvibr r-19yznuf r-64el8z r-1fkl15p r-1loqt21 r-o7ynqc r-6416eg r-1ny4l3l\"]");
//        nextButton1.click();
        System.out.println("Enter your password");
        WebElement passwordField = driverManager.visibilityOfElementLocated("[name=\"password\"][spellcheck=\"true\"]");
        passwordField.sendKeys(Properties.PASSWORD.val());

        WebElement loginButton = driverManager.elementToBeClickable("[data-testid=\"LoginForm_Login_Button\"][type=\"button\"]");
        loginButton.click();

        System.out.println("Logged into twitter");
    }

    private boolean checkIfLoggedIn() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {
        }
        return !AbstractDriverManager.getDriver().getCurrentUrl().equals(TWITTER_LOGIN_URL);
    }
}
