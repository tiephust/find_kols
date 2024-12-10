package twitter_kols;

import twitter_kols.conditions.TwitterLoggedIn;
import twitter_kols.core.find_users.FindFlowers;
import twitter_kols.core.find_users.FindKols;
import twitter_kols.core.handlers.LoginRequestHandler;
import twitter_kols.core.handlers.LogoutRequestHandler;
import twitter_kols.webdriver.ChromeDriverManager;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ChromeDriverManager chromeDriverManager = new ChromeDriverManager();
        LoginRequestHandler loginRequestHandler = new LoginRequestHandler();
        TwitterLoggedIn twitterLoggedIn = new TwitterLoggedIn();
        loginRequestHandler.login();
        FindKols findKols = new FindKols();
        findKols.findKols();
        List<String> kols = findKols.getListKoks();
        FindFlowers findFlowers = new FindFlowers();
        findFlowers.findMapKolsFlowers(kols);
        LogoutRequestHandler logoutRequestHandler = new LogoutRequestHandler();
        logoutRequestHandler.handleRequest();
        ChromeDriverManager.closeDriver();
    }
}