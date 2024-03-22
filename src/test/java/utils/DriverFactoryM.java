package utils;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

public class DriverFactoryM {

	private static ThreadLocal<WebDriver> localDriver;

	private static String browser = ConfigReader.getValue("config", "browser").toLowerCase();
	private static boolean headless;
	private static boolean mobile;
	private static String deviceName;
	private static boolean isSet;
	private static int waitTime = 5;

	private DriverFactoryM() {
		// Nothing should be written here.
	}

	private static void setUpDriver() {
		isSet = true;
	}

	public static synchronized WebDriver getDriver() {
		if (!isSet)
			setUpDriver();
		if (localDriver == null)
			localDriver = new ThreadLocal<WebDriver>();
		if (localDriver.get() == null)
			localDriver.set(initDriver());
		return localDriver.get();
	}

	public static void reset() {
		if (localDriver != null && localDriver.get() != null) {
			localDriver.get().quit();
			localDriver.remove();
		}
		isSet = false;
	}

	private static void configDriver(WebDriver driver) {
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitTime));
	}

	private static WebDriver initDriver() {
		WebDriver driver = null;
		switch (browser) {
		case "chrome":
			ChromeOptions options = new ChromeOptions();
			setChromeOptions(options);
			findChromeHeadless(options);
			emulateChromeIfMobile(options);
			driver = new ChromeDriver(options);
			break;
		case "edge":
			EdgeOptions edgeOptions = new EdgeOptions();
			setEdgeOptions(edgeOptions);
			findEdgeHeadless(edgeOptions);
			driver = new EdgeDriver(edgeOptions);
			break;
		case "firefox":
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			setFirefoxOptions(firefoxOptions);
			findFirefoxHeadless(firefoxOptions);
			driver = new FirefoxDriver(firefoxOptions);
			break;
		case "safari":
			assertTrue(AppTestUtils.isMac(), "Safari Driver is only allowed in Mac machines.");
			SafariOptions safariOptions = new SafariOptions();
			driver = new SafariDriver(safariOptions);
		default:
			fail("No such browser in the system.");
			break;
		}
		System.out.println(driver.toString().replaceAll("[(].*[)]", ""));
		configDriver(driver);
		return driver;
	}

	/**
	 * Set specific conditions of <Chrome> for this application
	 */
	private static void setChromeOptions(ChromeOptions options) {
		options.addArguments("--no-sandbox");
		if (Boolean.valueOf(ConfigReader.getValue("config", "incognito").toLowerCase()))
			options.addArguments("--incognito");
		String chromeUserDataPath = ConfigReader.getValue("config", "chromeUserDataPath");
		if (chromeUserDataPath != null) {
			options.addArguments(String.format("--user-data-dir=%s", chromeUserDataPath));
			options.addArguments(
					String.format("--profile-directory=%s", ConfigReader.getValue("config", "chromeProfile")));
		}
	}

	private static void findChromeHeadless(ChromeOptions options) {
		if (headless) {
			options.addArguments("--headless=new");
			options.addArguments("--user-agent=" + ConfigReader.getValue("config", "chromeUserAgent"));
		}
	}

	/**
	 * Change web view from desktop to either tablet or phone
	 */
	private static void emulateChromeIfMobile(ChromeOptions chromeOptions) {
		if (mobile && deviceName != null) {
			Map<String, String> mobileEmulation = new HashMap<>();
			mobileEmulation.put("deviceName", deviceName);
			chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
		}
	}

	/**
	 * Set specific conditions of <Edge> for this application
	 */
	private static void setEdgeOptions(EdgeOptions edgeOptions) {
		// create data type for value of capability "prefs"
		Map<String, Object> prefs = new HashMap<>();
		// turn off personal prompt
		prefs.put("user_experience_metrics.personalization_data_consent_enabled", true);
		edgeOptions.setExperimentalOption("prefs", prefs);
	}

	private static void findEdgeHeadless(EdgeOptions options) {
		if (headless)
			options.addArguments("--headless=new");
	}

	/**
	 * Set specific conditions of <Firefox> for this application
	 */
	private static void setFirefoxOptions(FirefoxOptions firefoxOptions) {
		// turn off geographical locator
		firefoxOptions.addPreference("geo.enabled", false);
		useFirefoxProfile(firefoxOptions);
		String firefoxBinPath = ConfigReader.getValue("config", "firefoxProfilePath");
		if (firefoxBinPath != null)
			firefoxOptions.setBinary(firefoxBinPath);
	}

	private static void useFirefoxProfile(FirefoxOptions firefoxOptions) {
		String firefoxProfilePath = ConfigReader.getValue("config", "firefoxProfilePath");
		if (firefoxProfilePath != null)
			firefoxOptions.addArguments("-profile", ConfigReader.getValue("config", firefoxProfilePath));
	}

	private static void findFirefoxHeadless(FirefoxOptions options) {
		if (headless)
			options.addArguments("-headless");
	}

}
