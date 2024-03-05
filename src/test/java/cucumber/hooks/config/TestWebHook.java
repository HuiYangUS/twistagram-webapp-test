package cucumber.hooks.config;

import io.cucumber.java.*;

import utilities.ConfigReader;
import utilities.DataManager;
import utilities.DriverManager;
import utilities.PageManager;

public class TestWebHook {

	@BeforeAll(order = 3)
	public static void printTestInfo() {
		if (Boolean.valueOf(ConfigReader.getValue("config", "demo")))
			System.out.println("Demo");
		else
			print();
	}

	private static void print() {
		String browserName = "Chrome";
		String data = System.getProperty("browser");
		if (data != null) {
			switch (data.strip().toLowerCase()) {
			case "chrome":
				break;
			case "edge":
				browserName = "Edge";
				break;
			case "firefox":
				browserName = "Firefox";
				break;
			case "safari":
			default:
				break;
			}
		}
		System.out.println("Test in " + browserName + ":");
	}

	@Before(order = 1, value = "@ui or @web")
	public void setUp() {
		DriverManager.getDriver();
		PageManager.getInstance();
		DataManager.getInstance();
	}

	@Before(order = 2)
	public void beforeTest() {
		System.out.println("Test starts:");
	}

	@After(order = 1, value = "@ui or @web")
	public void tearDown(Scenario scenario) {
		if (Boolean.valueOf(ConfigReader.getValue("config", "screenshot")) && scenario.isFailed())
			DataManager.getWebUtils().savesScreenshot();
		DriverManager.reset();
		PageManager.reset();
		DataManager.reset();
	}

	@After(order = 2)
	public void afterTest() {
		System.out.println("Test completed.");
	}

}
