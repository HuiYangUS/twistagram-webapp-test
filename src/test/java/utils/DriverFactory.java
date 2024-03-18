package utils;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverService;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;

/**
 * This <DriverFactory> class uses Selenium-4. Currently, Safari Driver is
 * disabled.
 */
public class DriverFactory {

	private static ThreadLocal<WebDriver> localDriver;

	private static String browser = "chrome";
	private static boolean auto = false;
	private static boolean headless = false;
	private static boolean mobile = false;
	private static String deviceName = null;
	private static boolean isSet = false;
	private static int waitTime = 5;

	private DriverFactory() {
	}

	public static String getBrowser() {
		return browser;
	}

	public static boolean isMobileOn() {
		return mobile;
	}

	public static String getDeviceName() {
		return deviceName;
	}

	private static void setUpDriver() {
		String browserKey = "browser";
		if (System.getProperty(browserKey) != null)
			browser = System.getProperty(browserKey).strip().toLowerCase();
		else {
			try {
				String browserTempValue = String.valueOf(ConfigReader.getValue("config", browserKey)).strip()
						.toLowerCase();
				List<String> browserNames = Arrays.asList("chrome", "edge", "firefox", "safari");
				if (browserNames.contains(browserTempValue))
					browser = browserTempValue;
				else
					throw new Exception();
			} catch (Exception e) {
				System.out.println("Default browser is used: " + browser);
			}
		}

		String autoKey = "auto";
		if (System.getProperty(autoKey) != null)
			auto = Boolean.valueOf(System.getProperty(autoKey).strip().toLowerCase());
		else {
			try {
				auto = Boolean.valueOf(ConfigReader.getValue("config", autoKey));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		String headlessKey = "headless";
		if (System.getProperty(headlessKey) != null)
			headless = Boolean.valueOf(System.getProperty(headlessKey).strip().toLowerCase());
		else {
			try {
				headless = Boolean.valueOf(ConfigReader.getValue("config", headlessKey));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		String mobileKey = "mobile";
		if (System.getProperty(mobileKey) != null)
			mobile = Boolean.valueOf(System.getProperty(mobileKey).strip().toLowerCase());
		else {
			try {
				mobile = Boolean.valueOf(ConfigReader.getValue("config", mobileKey));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		String deviceKey = "deviceName";
		if (System.getProperty(deviceKey) != null)
			deviceName = String.valueOf(System.getProperty(deviceKey).strip());
		else {
			try {
				deviceName = String.valueOf(ConfigReader.getValue("config", deviceKey));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		isSet = true;
	}

	public static synchronized WebDriver getDriver() {
		if (!isSet)
			setUpDriver();
		if (localDriver == null)
			localDriver = new ThreadLocal<WebDriver>();
		if (localDriver.get() == null)
			localDriver.set(getRealDriver());
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
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitTime));
	}

	/**
	 * Switch between local driver and auto driver
	 */
	private static WebDriver getRealDriver() {
		return auto ? autoLocalDriver() : initLocalDriver();
	}

	/**
	 * Local driver is updated manually
	 */
	private static WebDriver initLocalDriver() {
		WebDriver driver;
		switch (browser) {
		case "chrome":
			driver = getDefaultLocalDriver();
			break;
		case "edge":
			String edgeDriverFilePath = getDriverDir() + "/edgedriver/msedgedriver"
					+ (AppTestUtils.isWindows() ? ".exe" : "");
			EdgeDriverService edgeService = new EdgeDriverService.Builder()
					.usingDriverExecutable(new File(edgeDriverFilePath)).build();
			EdgeOptions edgeOptions = new EdgeOptions();
			setEdgeOptions(edgeOptions);
			findEdgeHeadless(edgeOptions);
			driver = new EdgeDriver(edgeService, edgeOptions);
			break;
		case "firefox":
			String firefoxDriverFilePath = getDriverDir() + "/firefoxdriver/geckodriver"
					+ (AppTestUtils.isWindows() ? ".exe" : "");
			FirefoxDriverService firefoxService = new GeckoDriverService.Builder()
					.usingDriverExecutable(new File(firefoxDriverFilePath)).build();
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			setFirefoxOptions(firefoxOptions);
			findFirefoxHeadless(firefoxOptions);
			driver = new FirefoxDriver(firefoxService, firefoxOptions);
			break;
		case "safari":
		default:
			driver = getDefaultLocalDriver();
			break;
		}
		System.out.println(driver.toString().replaceAll("[(].*[)]", ""));
		configDriver(driver);
		return driver;
	}

	private static WebDriver getDefaultLocalDriver() {
		String localDriverFilePath = getDriverDir() + "/chromedriver/chromedriver"
				+ (AppTestUtils.isWindows() ? ".exe" : "");
		ChromeDriverService service = new ChromeDriverService.Builder()
				.usingDriverExecutable(new File(localDriverFilePath)).build();
		ChromeOptions options = new ChromeOptions();
		setChromeOptions(options);
		findChromeHeadless(options);
		emulateChromeIfMobile(options);
		return new ChromeDriver(service, options);
	}

	/**
	 * Set specific conditions of <Chrome> for this application
	 */
	private static void setChromeOptions(ChromeOptions options) {
		options.addArguments(String.format("--user-data-dir=%s", ConfigReader.getValue("config", "userData")));
		options.addArguments(String.format("--profile-directory=%s", ConfigReader.getValue("config", "testProfile")));
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

	/**
	 * Set specific conditions of <Firefox> for this application
	 */
	private static void setFirefoxOptions(FirefoxOptions firefoxOptions) {
		// turn off geographical locator
		firefoxOptions.addPreference("geo.enabled", false);
		firefoxOptions.addArguments("-profile", ConfigReader.getValue("config", "firefoxProfile"));
	}

	/**
	 * Auto driver is updated automatically using Selenium Manager
	 */
	private static WebDriver autoLocalDriver() {
		System.err.println("Danger!!! Auto driver is used. Warning: Illegal downloaded driver could harm your system.");
		WebDriver driver;
		switch (browser) {
		case "chrome":
			driver = defaultAutoDriver();
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
		default:
			driver = defaultAutoDriver();
			break;
		}
		System.out.println(driver.toString().replaceAll("[(].*[)]", ""));
		configDriver(driver);
		return driver;
	}

	private static WebDriver defaultAutoDriver() {
		ChromeOptions options = new ChromeOptions();
		setChromeOptions(options);
		findChromeHeadless(options);
		emulateChromeIfMobile(options);
		return new ChromeDriver(options);
	}

	private static void findChromeHeadless(ChromeOptions options) {
		if (headless) {
			options.addArguments("--headless=new");
			options.addArguments("--user-agent=" + ConfigReader.getValue("config", "userAgent"));
		}
	}

	/**
	 * Change web view from desktop to either tablet or phone
	 */
	private static void emulateChromeIfMobile(ChromeOptions options) {
		if (mobile && deviceName != null) {
			Map<String, String> mobileEmulation = new HashMap<>();
			mobileEmulation.put("deviceName", deviceName);
			options.setExperimentalOption("mobileEmulation", mobileEmulation);
		}
	}

	private static void findEdgeHeadless(EdgeOptions options) {
		if (headless)
			options.addArguments("--headless=new");
	}

	private static void findFirefoxHeadless(FirefoxOptions options) {
		if (headless)
			options.addArguments("-headless");
	}

	/**
	 * Local drivers location
	 */
	public static String getDriverDir() {
		String dirPathName = null;
		if (AppTestUtils.isMac() && System.getProperty("os.arch").equalsIgnoreCase("x86_64"))
			dirPathName = "mac/intel";
		else if (AppTestUtils.isMac() && System.getProperty("os.arch").equalsIgnoreCase("aarch64"))
			dirPathName = "mac/m-chip";
		else if (AppTestUtils.isWindows())
			dirPathName = "win";
		else if (AppTestUtils.isLinux())
			dirPathName = "linux";
		assertNotNull(dirPathName, "Failed to locate a valid directory for the driver.");
		return AppTestUtils.getCurrentDir() + "/src/test/resources/drivers/" + dirPathName;
	}

}
