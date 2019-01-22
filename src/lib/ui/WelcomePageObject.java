package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class WelcomePageObject extends MainPageObject
{
    private static final String
    STEP_LEARN_MORE_LINK = "Learn more about Wikipedia",
    STEP_NEW_WAYS_TO_EXPLORE_TEXT = "New ways to explore",
    STEP_ADD_OR_EDIT_PREFERED_LANG_LINK = "Add or edit preferred languages",
    STEP_LEARN_MORE_ABOUT_DATA_COLLECTION = "Learn more about data collected",
    NEXT_LINK = "Next",
    GET_STARTED_BUTTON = "Get started";


    public WelcomePageObject(AppiumDriver driver) {
        super(driver);
    }

    public void waitForLearnMoreLink()
    {
        this.waitForElementPresent(By.id(STEP_LEARN_MORE_LINK),
                "Can't find 'Learn more about Wikipedia' link",
                10);
    }

    public void waitForNewWaysToExploreText()
    {
        this.waitForElementPresent(By.id(STEP_NEW_WAYS_TO_EXPLORE_TEXT),
                "Can't find 'New ways to explore' text",
                10);
    }

    public void waitForAddOrEditPreferredLanguages()
    {
        this.waitForElementPresent(By.id(STEP_ADD_OR_EDIT_PREFERED_LANG_LINK),
                "Can't find 'Add or edit preferred languages' link",
                10);
    }
    public void waitForLearnMoreAboutDataCollected()
    {
        this.waitForElementPresent(By.id(STEP_LEARN_MORE_ABOUT_DATA_COLLECTION),
                "Can't find 'Learn more about data collected' link",
                10);
    }

    public void clickNextButton()
    {
        this.waitForElementAndClick(By.id(NEXT_LINK),
                "Can't find 'Next' link",
                10);
    }

    public void clickGetStartedButton()
    {
        this.waitForElementAndClick(By.id(GET_STARTED_BUTTON),
                "Can't find 'Get started' button",
                10);
    }
}
