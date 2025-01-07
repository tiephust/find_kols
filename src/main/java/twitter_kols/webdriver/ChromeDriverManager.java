package twitter_kols.webdriver;

import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeDriverManager extends AbstractDriverManager {

    @Override
    public void setupDriver() {
    }

    @Override
    public void initDriver() {
        if (driver == null){
            driver = new ChromeDriver();
        }
    }
}
