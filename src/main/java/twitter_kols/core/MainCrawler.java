package twitter_kols.core;


import twitter_kols.core.handlers.LoginRequestHandler;
import twitter_kols.core.handlers.LogoutRequestHandler;
import twitter_kols.webdriver.ChromeDriverManager;

import java.io.IOException;

public class MainCrawler {
    public static void main(String[] args) throws InterruptedException, IOException {
        ChromeDriverManager chromeDriverManager = new ChromeDriverManager();
        LoginRequestHandler loginRequestHandler = new LoginRequestHandler();
        loginRequestHandler.handleRequest();
        ProcessData processData = new ProcessData();
        processData.processData();
        LogoutRequestHandler logoutRequestHandler = new LogoutRequestHandler();
        logoutRequestHandler.handleRequest();
        ChromeDriverManager.closeDriver();
    }
}
