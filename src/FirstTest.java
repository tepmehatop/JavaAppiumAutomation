import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
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
        driver.quit();
    }


    @Test
    public void saveTwoArticlesAndDeleteOneOfThem()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String first_search_line = "Java (programming language)";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                first_search_line,
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + first_search_line + "']"),
                "Cannot find topic searching by '" + first_search_line + "'",
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/title'][@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of articles folder",
                5
        );

        String name_of_folder = "Learning programming";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press OK button",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String second_search_line = "JavaScript";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                second_search_line,
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + second_search_line + "']"),
                "Cannot find topic searching by '" + second_search_line + "'",
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/title'][@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/item_container']//*[@text='" + name_of_folder + "']"),
                "Cannot find folder '" + name_of_folder + "' in list",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to 'My lists'",
                10
        );

        waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find created folder",
                10
        );

        waitForElementPresent(
                By.xpath("//*[@text='" + first_search_line + "']"),
                "Cannot find '" + first_search_line + "' topic in '" + name_of_folder + "' folder" ,
                5
        );

        waitForElementPresent(
                By.xpath("//*[@text='" + second_search_line + "']"),
                "Cannot find '" + second_search_line + "' topic in '" + name_of_folder + "' folder" ,
                5
        );

        swipeElementToLeft(
                By.xpath("//*[@text='" + second_search_line + "']"),
                "Cannot find saved article"
        );

        waitForElementNotPresent(
                By.xpath("//*[@text='" + second_search_line + "']"),
                "Cannot delete save article",
                5
        );

        waitForElementPresent(
                By.xpath("//*[@text='" + first_search_line + "']"),
                "Cannot find '" + first_search_line + "' topic in '" + name_of_folder + "' folder" ,
                5
        );


        waitForElementAndClick(
                By.xpath("//*[@text='" + first_search_line + "']"),
                "Cannot find '" + first_search_line + "' topic in '" + name_of_folder + "' folder" ,
                5
        );

        WebElement title_element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        String article_title = title_element.getAttribute("text");

        Assert.assertEquals(
                "We see unexpected title!",
                first_search_line,
                article_title
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



}




