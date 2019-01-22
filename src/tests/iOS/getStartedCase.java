package tests.iOS;

import lib.IOSTestCase;
import lib.ui.WelcomePageObject;
import org.junit.Test;

public class getStartedCase extends IOSTestCase {

    @Test
    public void testPassThroughWelcome()
    {
        WelcomePageObject WelcomePageObject = new WelcomePageObject(driver);
        WelcomePageObject.waitForLearnMoreLink();
        WelcomePageObject.clickNextButton();

        WelcomePageObject.waitForNewWaysToExploreText();
        WelcomePageObject.clickNextButton();

        WelcomePageObject.waitForAddOrEditPreferredLanguages();
        WelcomePageObject.clickNextButton();

        WelcomePageObject.waitForLearnMoreAboutDataCollected();
        WelcomePageObject.clickGetStartedButton();

    }
}
