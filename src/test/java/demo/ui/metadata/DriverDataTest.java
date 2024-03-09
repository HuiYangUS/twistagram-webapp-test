package demo.ui.metadata;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverInfo;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverInfo;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverInfo;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariDriverInfo;

import utilities.AppTestUtils;

@TestMethodOrder(OrderAnnotation.class)
public class DriverDataTest {

	@Test
	@EnabledOnOs(OS.MAC)
	@Order(value = 4)
	void sysSafariDriverDataTest() {
		SafariDriver driver = new SafariDriver();
		SafariDriverInfo driverInfo = new SafariDriverInfo();
		assertTrue(driverInfo.isPresent() && driverInfo.isAvailable(), "Driver is not present or available.");
		configDriver(driver);
		AppTestUtils.pause(1);
		Map<?, ?> data = driver.getCapabilities().asMap();
		printDriverData(data);
		shutDown(driver);
	}

	@Test
	@Order(value = 3)
	void sysFirefoxDriverDataTest() {
		FirefoxDriver driver = new FirefoxDriver();
		GeckoDriverInfo driverInfo = new GeckoDriverInfo();
		assertTrue(driverInfo.isPresent() && driverInfo.isAvailable(), "Driver is not present or available.");
		configDriver(driver);
		Map<?, ?> data = driver.getCapabilities().asMap();
		printDriverData(data);
		shutDown(driver);
	}

	@RepeatedTest(2)
	@EnabledOnOs(OS.WINDOWS)
	@Order(value = 7)
	@Tags(value = { @Tag("local"), @Tag("firefox") })
	void localFirefoxDriverDataTest() {
		FirefoxDriver driver = new FirefoxDriver(new GeckoDriverService.Builder()
				.usingDriverExecutable(new File(
						AppTestUtils.getCurrentDir() + "/src/test/resources/drivers/win/firefoxdriver/geckodriver.exe"))
				.build());
		GeckoDriverInfo driverInfo = new GeckoDriverInfo();
		assertTrue(driverInfo.isPresent() && driverInfo.isAvailable(), "Driver is not present or available.");
		configDriver(driver);
		Map<?, ?> data = driver.getCapabilities().asMap();
		System.out.println(String.format("%s: %s", "browserName", driver.getCapabilities().getBrowserName()));
		System.out.println(String.format("%s: %s", "geckodriverVersion", data.get("moz:geckodriverVersion")));
		System.out.println(String.format("%s: %s", "browserVersion", driver.getCapabilities().getBrowserVersion()));
		System.out.println(String.format("%s: %s", "cdpVersion", data.get("se:cdpVersion")));
		System.out.println(
				String.format("%s: %s", "implicitTimeout", ((Map<?, ?>) data.get("timeouts")).get("implicit")));
		shutDown(driver);
	}

	@Test
	@Order(value = 2)
	void sysEdgeDriverDataTest() {
		EdgeDriver driver = new EdgeDriver();
		EdgeDriverInfo driverInfo = new EdgeDriverInfo();
		assertTrue(driverInfo.isPresent() && driverInfo.isAvailable(), "Driver is not present or available.");
		configDriver(driver);
		AppTestUtils.pause(1);
		Map<?, ?> data = driver.getCapabilities().asMap();
		printDriverData(data);
		shutDown(driver);
	}

	@RepeatedTest(2)
	@EnabledOnOs(OS.WINDOWS)
	@Order(value = 6)
	@Tags(value = { @Tag("local"), @Tag("edge") })
	void localEdgeDriverDataTest() {
		EdgeOptions edgeOptions = new EdgeOptions();
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("user_experience_metrics.personalization_data_consent_enabled", true);
		edgeOptions.setExperimentalOption("prefs", prefs);
		EdgeDriver driver = new EdgeDriver(new EdgeDriverService.Builder()
				.usingDriverExecutable(new File(
						AppTestUtils.getCurrentDir() + "/src/test/resources/drivers/win/edgedriver/msedgedriver.exe"))
				.build(), edgeOptions);
		EdgeDriverInfo driverInfo = new EdgeDriverInfo();
		assertTrue(driverInfo.isPresent() && driverInfo.isAvailable(), "Driver is not present or available.");
		configDriver(driver);
		AppTestUtils.pause(1);
		Map<?, ?> data = driver.getCapabilities().asMap();
		System.out.println(String.format("%s: %s", "browserName", driver.getCapabilities().getBrowserName()));
		System.out.println(String.format("%s: %s", "msedgedriverVersion", ((Map<?, ?>) data.get("msedge"))
				.get("msedgedriverVersion").toString().replaceAll("[(].*[)]", "").strip()));
		System.out.println(String.format("%s: %s", "browserVersion", driver.getCapabilities().getBrowserVersion()));
		System.out.println(String.format("%s: %s", "cdpVersion", data.get("se:cdpVersion")));
		System.out.println(
				String.format("%s: %s", "implicitTimeout", ((Map<?, ?>) data.get("timeouts")).get("implicit")));
		shutDown(driver);
	}

	@Test
	@Order(value = 1)
	void sysChromeDriverDataTest() {
		ChromeDriver driver = new ChromeDriver();
		ChromeDriverInfo driverInfo = new ChromeDriverInfo();
		assertTrue(driverInfo.isPresent() && driverInfo.isAvailable(), "Driver is not present or available.");
		configDriver(driver);
		AppTestUtils.pause(1);
		Map<?, ?> data = driver.getCapabilities().asMap();
		printDriverData(data);
		shutDown(driver);
	}

	@RepeatedTest(2)
	@EnabledOnOs(OS.WINDOWS)
	@Order(value = 5)
	@Tags(value = { @Tag("local"), @Tag("chrome") })
	void localChromeDriverDataTest() {
		ChromeDriver driver = new ChromeDriver(new ChromeDriverService.Builder()
				.usingDriverExecutable(new File(
						AppTestUtils.getCurrentDir() + "/src/test/resources/drivers/win/chromedriver/chromedriver.exe"))
				.build());
		ChromeDriverInfo driverInfo = new ChromeDriverInfo();
		assertTrue(driverInfo.isPresent() && driverInfo.isAvailable(), "Driver is not present or available.");
		configDriver(driver);
		AppTestUtils.pause(1);
		Map<?, ?> data = driver.getCapabilities().asMap();
		System.out.println(String.format("%s: %s", "browserName", driver.getCapabilities().getBrowserName()));
		System.out.println(String.format("%s: %s", "chromedriverVersion", ((Map<?, ?>) data.get("chrome"))
				.get("chromedriverVersion").toString().replaceAll("[(].*[)]", "").strip()));
		System.out.println(String.format("%s: %s", "browserVersion", driver.getCapabilities().getBrowserVersion()));
		System.out.println(String.format("%s: %s", "cdpVersion", data.get("se:cdpVersion")));
		System.out.println(
				String.format("%s: %s", "implicitTimeout", ((Map<?, ?>) data.get("timeouts")).get("implicit")));
		shutDown(driver);
	}

	public static void configDriver(WebDriver driver) {
		driver.manage().window().maximize();
	}

	public static void shutDown(WebDriver driver) {
		try {

		} finally {
			driver.quit();
		}
	}

	private static void printDriverData(Map<?, ?> data) {
		for (Entry<?, ?> entry : data.entrySet()) {
			String key = entry.getKey().toString();
			String value = entry.getValue().toString();
			System.out.println(String.format("%s --> %s", key, value));
		}
	}

	@AfterEach
	void afterTest() {
		System.out.println();
	}

}
