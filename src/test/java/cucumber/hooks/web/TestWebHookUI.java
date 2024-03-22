package cucumber.hooks.web;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.AppTestUtils;
import utils.ConfigReader;
import utils.DataManager;
import utils.DriverManager;
import utils.PageManager;
import utils.TestConditions;

public class TestWebHookUI {

	@Before(order = 1, value = "@chrome")
	public void setupChrome() {
		TestConditions.browser = "chrome";
	}

	@Before(order = 1, value = "@firefox")
	public void setupFirefox() {
		TestConditions.browser = "firefox";
	}

	@Before(order = 2, value = "@ui or @web or @e2e")
	public void setUp() {
		DriverManager.getDriver();
		PageManager.getInstance();
		DataManager.getInstance();
	}

	@After(order = 2, value = "@ui or @web or @e2e")
	public void tearDown(Scenario scenario) {
		DataManager dataManager = DataManager.getInstance();
		if (Boolean.valueOf(ConfigReader.getValue("config", "screenshot")) && scenario.isFailed())
			dataManager.webUtils().savesScreenshot();
		DriverManager.reset();
		PageManager.reset();
		DataManager.reset();
		AppTestUtils.testConfigReset();
	}

}
