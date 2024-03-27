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

public class PostPage {

	private WebDriver driver;
	private WebDriverWait wait;

	@FindBy(id = ":r0:")
	private WebElement textbox;

	@FindBy(id = "post-button")
	private WebElement postButton;

	@FindBy(id = "notistack-snackbar")
	private WebElement alertBox;

	public PostPage() {
		driver = DriverManager.getDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(AppTestUtils.getTestConfigWaitTime()));
		PageFactory.initElements(driver, this);
	}

	public void postText(String text) {
		textbox.sendKeys(text);
	}

	public void clickOnPostButton() {
		wait.until(ExpectedConditions.elementToBeClickable(postButton)).click();
	}

	public String getAlertText() {
		return wait.until(ExpectedConditions.visibilityOf(alertBox)).getText();
	}

}
