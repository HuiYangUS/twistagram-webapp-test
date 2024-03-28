package cucumber.step_definitions;

import java.time.Duration;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pojos.ui.Profile;
import utils.AppTestUtils;
import utils.ConfigReader;
import utils.DataManager;
import utils.DriverManager;
import utils.PageManager;

public class ProfileSteps {

	private WebDriver driver = DriverManager.getDriver();
	private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(AppTestUtils.getTestConfigWaitTime()));
	private PageManager pages = PageManager.getInstance();
	private DataManager dataManager = DataManager.getInstance();

	private static String url = ConfigReader.getTextValue("config", "url");
	private static boolean demo = ConfigReader.getBooleanValue("config", "demo");

	@Given("user navigates to [Profile] page")
	public void user_navigates_to_profile_page() {
		pages.navBar().clickOnProfileBar();
	}

	@When("user clicks on [Edit Profile] button")
	public void user_clicks_on_edit_profile_button() {
		pages.profilePage().clickOnEditProfileButton();
	}

	@When("user enters new name, username and bio information")
	public void user_enters_new_name_username_and_bio_information(DataTable dataTable) {
		if (demo)
			AppTestUtils.pause(2);
		Map<String, String> data = dataTable.asMap();
		Profile profile = new Profile(data.get("name") + " " + AppTestUtils.getFormattedDateString(),
				data.get("username") + " " + AppTestUtils.getFormattedDateString(),
				data.get("bio") + AppTestUtils.getTimeStamp() + ".");
		dataManager.setProfile(profile);
		pages.editProfilePage().enterName(profile.name);
		pages.editProfilePage().enterUsername(profile.username);
		pages.editProfilePage().enterBio(profile.bio);
	}

	@When("user clicks on [Save] button")
	public void user_clicks_on_save_button() {
		pages.editProfilePage().clickOnSaveButton();
		wait.until(ExpectedConditions.urlContains(url + "profile"));
	}

	@Then("user sees new updated profile page")
	public void user_sees_new_updated_profile_page() {
		if (demo)
			AppTestUtils.pause(2);
		Profile profile = dataManager.getProfile();
		assertEquals(profile.getDisplayUsername(), pages.profilePage().getUsernameText());
		assertEquals(profile.bio, pages.profilePage().getBioText());
		System.out.println("Name: " + profile.name);
	}

}
