package twitter_kols;

import twitter_kols.conditions.TwitterLoggedIn;
import twitter_kols.core.find_users.FindKolsUrl;
import twitter_kols.core.ProcessData;
import twitter_kols.core.handlers.LoginRequestHandler;
import twitter_kols.core.handlers.LogoutRequestHandler;
import twitter_kols.webdriver.ChromeDriverManager;

import java.util.Set;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ChromeDriverManager chromeDriverManager = new ChromeDriverManager();
        LoginRequestHandler loginRequestHandler = new LoginRequestHandler();
        TwitterLoggedIn twitterLoggedIn = new TwitterLoggedIn();
        loginRequestHandler.login();
        FindKolsUrl findKols = new FindKolsUrl();
        findKols.findKols();
        Set<String> kols = findKols.getListKoks();
        ProcessData processData = new ProcessData();
        processData.processData(kols);
        LogoutRequestHandler logoutRequestHandler = new LogoutRequestHandler();
        logoutRequestHandler.handleRequest();
        ChromeDriverManager.closeDriver();
    }
}