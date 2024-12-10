package twitter_kols.main.java.twitter_kols;

import twitter_kols.core.handlers.LoginRequestHandler;
import twitter_kols.core.handlers.LogoutRequestHandler;
import twitter_kols.webdriver.ChromeDriverManager;

public class Main {
    public static void main(String[] args) {
        ChromeDriverManager chromeDriverManager = new ChromeDriverManager();
        LoginRequestHandler loginRequestHandler = new LoginRequestHandler();
        loginRequestHandler.login();
        LogoutRequestHandler logoutRequestHandler = new LogoutRequestHandler();
        logoutRequestHandler.handleRequest();
//        ChromeDriverManager.closeDriver();
    }
}