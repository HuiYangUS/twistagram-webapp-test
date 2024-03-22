package cucumber.step_definitions;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import utils.AppTestUtils;
import utils.ConfigReader;
import utils.DriverManager;
import utils.PageManager;

public class LoginSteps {

	private WebDriver driver = DriverManager.getDriver();
	private WebDriverWait wait = new WebDriverWait(driver,
			Duration.ofSeconds(Long.valueOf(ConfigReader.getValue("config", "waitTime"))));
	private PageManager pages = PageManager.getInstance();

	private static String url = ConfigReader.getValue("config", "url");

	@Given("user navigates to [Twistagram] application")
	public void user_navigates_to_twistagram_application() {
		driver.navigate().to(url);
	}

	@When("user clicks on [Log in with Google] button")
	public void user_clicks_on_log_in_with_google_button() {
		pages.signInPage().clickLoginWithGoogle();
	}

	@When("user enters gmail")
	public void user_enters_gmail() {
		pages.googleAccountPage().enterGoogleEmail();
	}

	@When("user enters gmail password")
	public void user_enters_gmail_password() {
		pages.googleAccountPage().enterPassword();
	}

	@When("user confirms to login by clicking [Continue] button")
	public void user_confirms_to_login_by_clicking_continue_button() {
		pages.googleAccountPage().confirmByClickContinue();
	}

	@Then("user is logged in and is on the [Home] page")
	public void user_is_logged_in_and_is_on_the_home_page() {
		wait.until(ExpectedConditions.urlToBe(url));
		assertTrue(pages.navBar().isHomeIconInFocusVisible(), "Home Icon is not visible.");
		System.out.println(pages.homePage().getLoggedInText());
		boolean demo = Boolean.valueOf(ConfigReader.getValue("config", "demo").toLowerCase());
		if (demo)
			AppTestUtils.pause(3);
	}

}
