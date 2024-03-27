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

public class NavBar {

	private WebDriver driver;
	private WebDriverWait wait;

	private static String url = ConfigReader.getTextValue("config", "url");

	@FindBy(css = "img[alt='twistagram-logo']")
	private WebElement logo;

	@FindBy(xpath = "//span[text()='Home']")
	private WebElement homeIconText;

	@FindBy(xpath = "//*[name()='svg' and @data-testid='HomeIcon']")
	private WebElement homeIconInFocus;

	@FindBy(xpath = "//*[name()='svg' and @data-testid='HomeOutlinedIcon']")
	private WebElement homeIconOutFocus;

	@FindBy(xpath = "//div/span[text()='Post']/parent::div")
	private WebElement postBar;

	public NavBar() {
		driver = DriverManager.getDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(AppTestUtils.getTestConfigWaitTime()));
		PageFactory.initElements(driver, this);
	}

	public boolean isHomeIconInFocusVisible() {
		try {
			wait.until(ExpectedConditions.visibilityOf(homeIconInFocus));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void clickOnPostBar() {
		postBar.click();
		wait.until(ExpectedConditions.urlToBe(url + "post"));
	}

}
