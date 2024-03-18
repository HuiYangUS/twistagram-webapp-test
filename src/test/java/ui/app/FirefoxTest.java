package ui.app;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import ui.base.web.DriverFactoryWebBase;
import utils.ConfigReader;

public class FirefoxTest extends DriverFactoryWebBase {

	private static String url = ConfigReader.getValue("config", "url");

	@BeforeAll
	static void setupHeadlessMode() {
		System.setProperty("browser", "firefox");
	}

	@Test
	@DisplayName("Twista Gram App Google Login Test - Firefox Edition")
	@Tags(value = { @Tag("main"), @Tag("smoke") })
	void runTest() {
		driver.navigate().to(url);
		String expectedTitle = "Twistagram";
		assertEquals(expectedTitle, driver.getTitle());
		try {
			driver.findElement(By.linkText("Sign out")).click();
			driver.findElement(By.id("submitButton")).click();
		} catch (Exception e) {
			// TODO: handle exception
		}
		driver.findElement(By.xpath("//button//*[name()='svg' and @data-testid='GoogleIcon']/ancestor::button"))
				.click();
		String emailXpath = String.format("//div[@data-email='%s']/..", ConfigReader.getValue("config", "email"));
		webUtils.mouse().click(driver.findElement(By.xpath(emailXpath))).perform();
		wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//button//span[text()='Continue']/ancestor::button")))
				.click();
		String tempXpath = "//main/div/div/span";
		String expectedHomeMessage = String.format("Logged in as %s", ConfigReader.getValue("config", "fullName"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tempXpath))).getText();
		String actualHomeMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tempXpath)))
				.getText();
		assertEquals(expectedHomeMessage, actualHomeMessage);
		webUtils.savesScreenshot("firefox", true);
	}

}
