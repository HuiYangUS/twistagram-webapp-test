package cucumber.hooks.web;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.AppTestUtils;
import utils.ConfigReader;
import utils.DataManager;
import utils.DriverManager;
import utils.PageManager;

public class TestWebHookUI {

	@Before(order = 1, value = "@profile")
	public void useProfile() {
		setupChrome();
		System.setProperty("profile", "true");
	}

	@Before(order = 1, value = "@chrome")
	public void useChrome() {
		setupChrome();
	}

	@Before(order = 1, value = "@firefox")
	public void useFirefox() {
		System.setProperty("browser", "firefox");
	}

	private static String phoneName = "iPhone SE";
	private static String tabletName = "iPad Mini";

	@Before(order = 1, value = "@phone")
	public void setupPhone() {
		setupChrome();
		System.setProperty("deviceName", phoneName);
	}

	@Before(order = 1, value = "@tablet")
	public void setupTablet() {
		setupChrome();
		System.setProperty("deviceName", tabletName);
	}

	private static void setupChrome() {
		System.setProperty("browser", "chrome");
	}

	@After
	public void tearDownMobile() {
		System.clearProperty("deviceName");
	}

	@Before(order = 2, value = "@ui or @web or @e2e or @phone or @tablet")
	public void setUp() {
		DriverManager.getDriver();
		PageManager.getInstance();
		DataManager.getInstance();
	}

	@After(order = 2, value = "@ui or @web or @e2e or @phone or @tablet")
	public void tearDown(Scenario scenario) {
		DataManager dataManager = DataManager.getInstance();
		if (ConfigReader.getBooleanValue("config", "screenshot") && scenario.isFailed())
			dataManager.webUtils().savesScreenshot();
		DriverManager.reset();
		PageManager.reset();
		DataManager.reset();
		AppTestUtils.testConfigReset();
	}

}
