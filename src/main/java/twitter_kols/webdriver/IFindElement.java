package twitter_kols.webdriver;

import org.openqa.selenium.WebElement;

import java.util.List;

public interface IFindElement {

    WebElement visibilityOfElementLocated(String locator);
    List<WebElement> visibilityOfAllElementsLocatedBy(String locator);
    WebElement elementToBeClickable(String locator);

}
