package ui.app;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;

import ui.base.web.DriverFactoryWebBase;
import utilities.ConfigReader;

public class HeadlessCheckTest extends DriverFactoryWebBase {

	@BeforeAll
	static void setupHeadlessMode() {
		System.setProperty("headless", "true");
	}

	private static String url = ConfigReader.getValue("config", "url");

	@Test
	@Disabled(value = "still trying to configure headless options to allow google account to login")
	@DisplayName("Twista Gram App Google Login Test - Headless Mode")
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
		String emailXpath = String.format("//div[@data-email='%s']", ConfigReader.getValue("config", "email"));
		driver.findElement(By.xpath(emailXpath)).click();
		driver.findElement(By.xpath("//button//span[text()='Continue']/ancestor::button")).click();
		String tempXpath = "//main/div/div/span";
		String expectedHomeMessage = String.format("Logged in as %s", ConfigReader.getValue("config", "fullName"));
		String actualHomeMessage = driver.findElement(By.xpath(tempXpath)).getText();
		assertEquals(expectedHomeMessage, actualHomeMessage);
	}

}
