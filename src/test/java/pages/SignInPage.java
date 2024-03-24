package pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.AppTestUtils;
import utils.DriverManager;

public class SignInPage {

	private WebDriver driver;
	private WebDriverWait wait;

	@FindBy(xpath = "//button//*[name()='svg' and @data-testid='GoogleIcon']/ancestor::button")
	private WebElement googleLogInButton;

	public SignInPage() {
		driver = DriverManager.getDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(AppTestUtils.getTestConfigWaitTime()));
		PageFactory.initElements(driver, this);
	}

	public void clickLoginWithGoogle() {
		wait.until(ExpectedConditions.visibilityOf(googleLogInButton)).click();
	}

}
