package utils;

import org.openqa.selenium.WebDriver;

public class DriverManager {

	private DriverManager() {
	}

	public static WebDriver getDriver() {
		return DriverFactory.getDriver();
	}

	public static void reset() {
		DriverFactory.reset();
	}

}
