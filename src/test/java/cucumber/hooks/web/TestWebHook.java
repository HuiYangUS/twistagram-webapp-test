package cucumber.hooks.web;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import utils.ConfigReader;
import utils.DataManager;
import utils.DriverManager;
import utils.PageManager;

public class TestWebHook {

	@Before(order = 2, value = "@ui or @web")
	public void setUp() {
		DriverManager.getDriver();
		PageManager.getInstance();
		DataManager.getInstance();
	}

	@Before(order = 3)
	public void beforeTest() {
		System.out.println("Test starts:");
	}

	@After(order = 2, value = "@ui or @web")
	public void tearDown(Scenario scenario) {
		if (Boolean.valueOf(ConfigReader.getValue("config", "screenshot")) && scenario.isFailed())
			DataManager.getWebUtils().savesScreenshot();
		DriverManager.reset();
		PageManager.reset();
		DataManager.reset();
	}

	@After(order = 3)
	public void afterTest() {
		System.out.println("Test completed.");
	}

}
