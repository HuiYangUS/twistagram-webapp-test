package demo.ui.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import demo.ui.base.web.DriverFactoryWebBase;

public class DemoTest extends DriverFactoryWebBase {

	private static String url = "http://localhost:3000";

	@Test
	@DisplayName("Demo Twista Gram App Test")
	void runTest() {
		driver.navigate().to(url);
		String expectedTitle = "Twistagram";
		assertEquals(expectedTitle, driver.findElement(By.tagName("h3")).getText());
	}

}
