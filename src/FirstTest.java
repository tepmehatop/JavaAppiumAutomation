import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;


public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "C:\\Users\\User\\Desktop\\JavaAppiumAutomation\\apks\\org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void teatDown()
    {
        driver.quit();
    }

    @Test
    public void checkFindedTitle()
    {

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Search field not found on the page",
                5
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "BMW",
                "Can't find input field",
                5
        );


        checkContainsTextFindedElements(
                By.id("org.wikipedia:id/page_list_item_title"),
                "BMW"
        );

    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutWaitSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutWaitSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by,String search_text, String error_message, long timeoutWaitSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutWaitSeconds);
        element.sendKeys(search_text);
        return element;
    }


    private void checkContainsTextFindedElements (By by, String finedText)
    {
        List<WebElement> elements = driver.findElements(by);
        for(WebElement element1: elements) {
            Assert.assertTrue(element1.getText().contains(finedText));
        }
        return;

    }

    public WebElement waitForElementPresent(By by, String error_message, long timeoutWaitSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutWaitSeconds);
        wait.withMessage(error_message + "\n");
                return wait.until(
                        ExpectedConditions.presenceOfElementLocated(by)
                );
    }

    public WebElement waitForElementPresent(By by, String error_message)
    {
       return waitForElementPresent(by, error_message, 5);
    }
}
