package ui.demo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import ui.base.web.DriverFactoryWebBase;
import utils.AppTestUtils;
import utils.ConfigReader;

public class DemoTest extends DriverFactoryWebBase {

	private static String url = ConfigReader.getValue("config", "url");
	private static boolean demo = Boolean.valueOf(ConfigReader.getValue("config", "demo"));
	private static int demoTime = 3;

	@Test
	@DisplayName("Twista Gram App Google Demo Test")
	@Disabled(value = "Legacy issue was resolved. This test is not needed at this moment.")
	@EnabledIf("isDemoTime")
	@Tags(value = { @Tag("demo"), @Tag("smoke") })
	void runTest() {
		driver.navigate().to(url);
		AppTestUtils.pause(demoTime);
		String expectedTitle = "Twistagram";
		assertEquals(expectedTitle, driver.getTitle());
		driver.findElement(By.xpath("//button//*[name()='svg' and @data-testid='GoogleIcon']/ancestor::button"))
				.click();
		String emailXpath = String.format("//div[@data-email='%s']/..", ConfigReader.getValue("config", "email"));
		webUtils.mouse().click(driver.findElement(By.xpath(emailXpath))).perform();
		wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//button//span[text()='Continue']/ancestor::button")))
				.click();
		AppTestUtils.pause(demoTime);
	}

	static boolean isDemoTime() {
		return demo;
	}

}
