import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
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
       // capabilities.setCapability("udid", "G2AZCY096767");
        capabilities.setCapability("udid", "192.168.51.101:5555");
        capabilities.setCapability("platformVersion", "6.0.1");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "C:\\Users\\User\\Desktop\\JavaAppiumAutomation\\apks\\org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void teatDown()
    {
        driver.rotate(ScreenOrientation.PORTRAIT);
        driver.quit();
    }


    @Test
    public void testChangeScreenOrientationOnSearchResults()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String search_line = "Java";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_line,
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by '" + search_line + "'",
                5
        );

        String title_before_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String title_after_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );

        Assert.assertEquals(
                "Article title have been changed after screen rotation ",
                title_before_rotation,
                title_after_rotation
        );

        driver.rotate(ScreenOrientation.PORTRAIT);

        String title_after_second_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15
        );

        Assert.assertEquals(
                "Article title have been changed after screen rotation ",
                title_before_rotation,
                title_after_second_rotation
        );
    }


    public boolean isElementPresent(By by)
    {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
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

    protected void swipeUp (int timeOfSwipe)
    {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width /2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);

        action.press(x, start_y).waitAction(timeOfSwipe).moveTo(x, end_y).release().perform();
    }


    protected void swipeUpQuick()
        {
        swipeUp(600);
        }

        protected void swipeToFindedElement (By by, String error_message, int max_swipes)
        {
            int already_swiped = 0;
            while (driver.findElements(by).size() == 0){

                if (already_swiped > max_swipes) {
                    waitForElementPresent(by, "Element not found by swiping up \n" + error_message, 0);
                    return;
                }
                swipeUpQuick();
                ++already_swiped;
            }
        }

    protected void swipeElementToLeft(By by, String error_message)
    {
        WebElement element = waitForElementPresent(
                by,
                error_message,
                10);
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(right_x, middle_y)
                .waitAction(300)
                .moveTo(left_x, middle_y)
                .release()
                .perform();
    }


    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait  wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }


    private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        return element.getAttribute(attribute);
    }


}




