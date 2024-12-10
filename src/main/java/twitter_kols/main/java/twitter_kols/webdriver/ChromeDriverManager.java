package twitter_kols.main.java.twitter_kols.webdriver;

import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeDriverManager extends IDriverManager {

    // Phương thức setupDriver sẽ cấu hình và tải ChromeDriver.
    @Override
    public void setupDriver() {
    }

    // Phương thức initDriver sẽ khởi tạo trình duyệt Chrome với các tùy chọn đã cấu hình.
    @Override
    public void initDriver() {
        if (driver == null){
            driver = new ChromeDriver();
        }
    }
}
