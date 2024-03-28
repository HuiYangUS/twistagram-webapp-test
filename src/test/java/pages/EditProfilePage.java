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
import utils.WebUtils;

public class EditProfilePage {

	private WebDriver driver;
	private WebDriverWait wait;
	private WebUtils utils;

	@FindBy(xpath = "//label[text()='Name']/following-sibling::div/input")
	private WebElement nameInput;

	@FindBy(xpath = "//label[text()='Username']/following-sibling::div/input")
	private WebElement usernameInput;

	@FindBy(xpath = "//label[text()='Bio']/following-sibling::div/textarea")
	private WebElement bioTextbox;

	@FindBy(id = "save-button")
	private WebElement saveButton;

	public EditProfilePage() {
		driver = DriverManager.getDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(AppTestUtils.getTestConfigWaitTime()));
		utils = new WebUtils(driver);
		PageFactory.initElements(driver, this);
	}

	public void enterName(String name) {
		utils.forceClear(nameInput);
		nameInput.sendKeys(name);
		wait.until(ExpectedConditions.attributeToBe(nameInput, "value", name));
	}

	public void enterUsername(String username) {
		usernameInput.clear();
		usernameInput.sendKeys(username);
		wait.until(ExpectedConditions.attributeToBe(usernameInput, "value", username));
	}

	public void enterBio(String bio) {
		utils.forceClear(bioTextbox);
		bioTextbox.sendKeys(bio);
		wait.until(ExpectedConditions.textToBePresentInElement(bioTextbox, bio));
	}

	public void clickOnSaveButton() {
		saveButton.click();
	}

}
