package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.AppTestUtils;
import utils.ConfigReader;
import utils.DriverManager;

public class GoogleAccountPage {

	private WebDriver driver;
	private WebDriverWait wait;

	private static String url = "https://accounts.google.com/";
	private static String email = ConfigReader.getTextValue("config", "email");

	@FindBy(xpath = "//input[@type='email']")
	private WebElement googleEmailInput;

	@FindBy(xpath = "//input[@type='password']")
	private WebElement passwordInput;

	@FindBy(xpath = "//span[text()='Sign in to re-boot.us']")
	private WebElement signInConfirmTitle;

	@FindBy(xpath = "//span[text()='Continue']")
	private WebElement signInConfirmContinueButton;

	public GoogleAccountPage() {
		driver = DriverManager.getDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(AppTestUtils.getTestConfigWaitTime()));
		PageFactory.initElements(driver, this);
	}

	public void enterGoogleEmail() {
		wait.until(ExpectedConditions.urlContains(url));
		wait.until(ExpectedConditions.visibilityOf(googleEmailInput))
				.sendKeys(ConfigReader.getTextValue("config", "email") + Keys.ENTER);
	}

	public void enterPassword() {
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector(String.format("[data-email='%s']", email))));
		wait.until(ExpectedConditions.visibilityOf(passwordInput))
				.sendKeys(ConfigReader.getTextValue("config", "password") + Keys.ENTER);
	}

	public void confirmByClickContinue() {
		wait.until(ExpectedConditions.visibilityOf(signInConfirmTitle));
		wait.until(ExpectedConditions.visibilityOf(signInConfirmContinueButton)).click();
	}

}
