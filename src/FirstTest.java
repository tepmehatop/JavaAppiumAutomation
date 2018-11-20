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
    public void checkSearchFieldText()
    {

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Search field not found on the page",
                5
        );


        WebElement searchFieldPlaceholderPresent =  waitForElementPresent(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Placeholder of Search field not found"
        );

        String search_field = searchFieldPlaceholderPresent.getAttribute("text");

        Assert.assertEquals(
                "Unexpected placeholder in search field",
                "Search…",
                search_field);

    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutWaitSeconds) {

        WebElement element = waitForElementPresent(by, error_message, timeoutWaitSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementPresent(By by, String error_message, long timeInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeInSeconds);
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
