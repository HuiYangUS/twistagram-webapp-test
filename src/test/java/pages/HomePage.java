package pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.ConfigReader;
import utils.DriverManager;

public class HomePage {

	private WebDriver driver;
	private WebDriverWait wait;

	@FindBy(xpath = "//a[@href='/api/auth/signout']/preceding-sibling::div/span")
	private WebElement loggedInText;

	public HomePage() {
		driver = DriverManager.getDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(Long.valueOf(ConfigReader.getValue("config", "waitTime"))));
		PageFactory.initElements(driver, this);
	}

	public String getLoggedInText() {
		return wait.until(ExpectedConditions.visibilityOf(loggedInText)).getText();
	}

}
