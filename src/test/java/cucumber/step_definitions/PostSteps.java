package cucumber.step_definitions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.AppTestUtils;
import utils.ConfigReader;
import utils.DriverManager;
import utils.PageManager;

public class PostSteps {

	private WebDriver driver = DriverManager.getDriver();
	private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(AppTestUtils.getTestConfigWaitTime()));
	private PageManager pages = PageManager.getInstance();

	private static String url = ConfigReader.getTextValue("config", "url");
	private static boolean demo = ConfigReader.getBooleanValue("config", "demo");

	@Given("user is logged in")
	public void user_is_logged_in() {
		driver.navigate().to(url);
		wait.until(ExpectedConditions.urlToBe(url));
	}

	@Given("user navigates to [Post] page")
	public void user_navigates_to_post_page() {
		pages.navBar().clickOnPostBar();
	}

	@When("user enters {string} in the textbox")
	public void user_enters_in_the_textbox(String message) {
		pages.postPage().postText(message);
		if (demo)
			AppTestUtils.pause(2);
	}

	@When("user clicks on [Post] button")
	public void user_clicks_on_post_button() {
		pages.postPage().clickOnPostButton();
	}

	@Then("user sees success {string} message")
	public void user_sees_success_message(String successText) {
		String expectedText = successText;
		assertEquals(expectedText, pages.postPage().getAlertText());
		if (demo)
			AppTestUtils.pause(2);
	}

}
