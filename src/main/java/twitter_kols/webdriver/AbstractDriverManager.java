package twitter_kols.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class AbstractDriverManager implements IFindElement {
    protected static WebDriver driver;
    protected static WebDriverWait webDriverWait;

    public AbstractDriverManager() {
        this.setupDriver();
        this.initDriver();
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(50));
    }

    abstract protected void setupDriver();

    abstract protected void initDriver();

    public static WebDriver getDriver() {
        return driver;
    }

    public static WebDriverWait getWebDriverWait() {
        return webDriverWait;
    }

    @Override
    public WebElement visibilityOfElementLocated(String locator) {
        return webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locator)));
    }

    @Override
    public List<WebElement> visibilityOfAllElementsLocatedBy(String locator) {
        return webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(locator)));
    }

    @Override
    public WebElement elementToBeClickable(String locator) {
        return webDriverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(locator)));
    }

    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
