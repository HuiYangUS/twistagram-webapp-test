package ui.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import org.openqa.selenium.support.ui.ExpectedConditions;

import ui.base.web.DriverFactoryWebBase;
import utils.AppTestUtils;
import utils.ConfigReader;

public class LoginTest extends DriverFactoryWebBase {

	private static boolean demo = Boolean.valueOf(ConfigReader.getValue("config", "demo").toLowerCase());

	private static String url = ConfigReader.getValue("config", "url");

	@Test
	@DisplayName("TwistaGram Google Login Test")
	@EnabledIf("isDemo")
	@Tags(value = { @Tag("google"), @Tag("sanity"), @Tag("demo") })
	void googleLoginTest() {
		driver.navigate().to(url);
		pages.signInPage().clickLoginWithGoogle();
		pages.googleAccountPage().enterGoogleEmail();
		pages.googleAccountPage().enterPassword();
		pages.googleAccountPage().confirmByClickContinue();
		wait.until(ExpectedConditions.urlToBe(url));
		assertTrue(pages.navBar().isHomeIconInFocusVisible(), "Home Icon is not visible.");
		System.out.println(pages.homePage().getLoggedInText());
		if (demo)
			AppTestUtils.pause(3);
	}

	static boolean isDemo() {
		return demo;
	}

}
