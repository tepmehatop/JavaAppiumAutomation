package tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

    public class Ex6AssertTitle {

        private AppiumDriver driver;

        @Before
        public void setUp() throws Exception {
            DesiredCapabilities capabilities = new DesiredCapabilities();

            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("deviceName", "AndroidDevice");
            capabilities.setCapability("platformVersion", "8.0");
            capabilities.setCapability("udid", "192.168.51.101:5555");
            capabilities.setCapability("automationName", "Appium");
            capabilities.setCapability("appPackage", "org.wikipedia");
            capabilities.setCapability("appActivity", ".main.MainActivity");
            capabilities.setCapability("app", "C:\\Users\\User\\Desktop\\JavaAppiumAutomation\\apks\\org.wikipedia.apk");

            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

        }

        @After
        public void tearDown() {
            driver.quit();
        }

        @Test
        public void checkArticleTitleAvailability() throws InterruptedException
        {
            waitForElementAndClick(
                    By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                    "Can't find 'Search Wikipedia' input",
                    5
            );
            String search_input = "Appium";

            waitForElementAndSendKeys(
                    By.xpath("//*[contains(@text, 'Search…')]"),
                    search_input,
                    "Can't find search input",
                    5
            );

            waitForElementAndClick(
                    By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + search_input + "']"),
                    "Can't find '" +search_input+ "' topic searching by " + search_input,
                    15
            );

            Thread.sleep(5000);

            assertElementPresent(
                    By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text']"),
                    " Can't find page title"
            );
        }

        private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds)
        {
            WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
            wait.withMessage(error_message + "/n");
            return wait.until(
                    ExpectedConditions.presenceOfElementLocated(by)
            );
        }

        private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds)
        {
            WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
            element.click();
            return element;
        }

        private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {
            WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
            element.sendKeys(value);
            return element;
        }

        private int getAmountOfElements(By by)
        {
            List elements = driver.findElements(by);
            return elements.size();

        }

        private void assertElementPresent(By by, String error_message)
        {
            int amount_of_elements = getAmountOfElements(by);
            if (amount_of_elements == 0){
                String default_message = "An element '" + by.toString() + "' is not found on the page";
                throw new AssertionError(default_message + error_message);
            }
        }
    }

