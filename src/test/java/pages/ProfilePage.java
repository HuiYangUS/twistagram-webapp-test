package pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.AppTestUtils;
import utils.ConfigReader;
import utils.DriverManager;

public class ProfilePage {

	private WebDriver driver;
	private WebDriverWait wait;

	private static String url = ConfigReader.getTextValue("config", "url");

	@FindBy(css = "button[tabindex='0'][type='button']")
	WebElement editProfileButton;

	public ProfilePage() {
		driver = DriverManager.getDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(AppTestUtils.getTestConfigWaitTime()));
		PageFactory.initElements(driver, this);
	}

	public void clickOnEditProfileButton() {
		editProfileButton.click();
		wait.until(ExpectedConditions.urlToBe(url + "profile/edit"));
	}

}
