package demo.ui.app;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

import demo.ui.base.web.DriverFactoryWebBase;
import utilities.ConfigReader;

public class InitialTest extends DriverFactoryWebBase {

	private static String url = ConfigReader.getValue("config", "url");

	@Test
	@DisplayName("Demo Twista Gram App Test")
	@Tags(value = { @Tag("home"), @Tag("smoke"), @Tag("TWIS-43") })
	void runTest() {
		driver.navigate().to(url);
		String expectedTitle = "Twistagram";
		assertEquals(expectedTitle, driver.getTitle());
		webUtils.savesScreenshot("home", true);
	}

}
