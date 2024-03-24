package utils;

import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.WebDriver;

public class DriverManager {

	private DriverManager() {
		// WARN: Nothing should be written here.
	}

	public static WebDriver getDriver() {
		String driverFactoryType = ConfigReader.getValue("config", "driverFactoryType");
		if (driverFactoryType.equalsIgnoreCase("p") || driverFactoryType.equalsIgnoreCase("pie")) {
			return DriverFactoryPie.getDriver();
		}
		if (driverFactoryType.equalsIgnoreCase("m"))
			return DriverFactoryM.getDriver();
		fail("No valid Driver Factory type in the system.");
		return null;
	}

	public static void reset() {
		DriverFactoryPie.reset();
		DriverFactoryM.reset();
	}

}
