package utils;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
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
 * This <DriverFactory> class uses Selenium-4. Drivers are updated manually in
 * "drivers" folder under "resources" package.
 */
public class DriverFactoryPie {

	private static ThreadLocal<WebDriver> localDriver;

	private static String browser = AppTestUtils.getTestConfigBrowserName();
	private static boolean headless = ConfigReader.getBooleanValue("config", "headless");
	private static String deviceName = ConfigReader.getTextValue("config", "deviceName");
	private static boolean isSet;
	private static int waitTime = 5;

	private DriverFactoryPie() {
		// WARN: Nothing should be written here.
	}

	private static void setUpDriver() {
		String browserKey = "browser";
		if (System.getProperty(browserKey) != null)
			browser = System.getProperty(browserKey).toLowerCase();
		String headlessKey = "headless";
		if (System.getProperty(headlessKey) != null)
			headless = Boolean.valueOf(System.getProperty(headlessKey).toLowerCase());
		String deviceNameKey = "deviceName";
		if (System.getProperty(deviceNameKey) != null)
			deviceName = System.getProperty(deviceNameKey);
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
			AppTestUtils.reallyQuitThisDriver(localDriver.get());
			localDriver.remove();
		}
		isSet = false;
		AppTestUtils.testConfigReset();
	}

	private static void configDriver(WebDriver driver) {
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitTime));
	}

	private static WebDriver initDriver() {
		WebDriver driver = null;
		switch (browser) {
		case "chrome":
			String chromeDriverPath = getDriverDir() + "/chromedriver/chromedriver"
					+ (AppTestUtils.isWindows() ? ".exe" : "");
			ChromeDriverService service = new ChromeDriverService.Builder()
					.usingDriverExecutable(new File(chromeDriverPath)).build();
			ChromeOptions options = new ChromeOptions();
			setChromeOptions(options);
			findChromeHeadless(options);
			emulateChromeIfMobile(options);
			driver = new ChromeDriver(service, options);
			break;
		case "edge":
			assertTrue(AppTestUtils.isLinux(), "Edge Driver is only allowed in Linux systems.");
			String edgeDriverPath = getDriverDir() + "/edgedriver/msedgedriver"
					+ (AppTestUtils.isWindows() ? ".exe" : "");
			EdgeDriverService edgeService = new EdgeDriverService.Builder()
					.usingDriverExecutable(new File(edgeDriverPath)).build();
			EdgeOptions edgeOptions = new EdgeOptions();
			setEdgeOptions(edgeOptions);
			findEdgeHeadless(edgeOptions);
			driver = new EdgeDriver(edgeService, edgeOptions);
			break;
		case "firefox":
			String firefoxDriverPath = getDriverDir() + "/firefoxdriver/geckodriver"
					+ (AppTestUtils.isWindows() ? ".exe" : "");
			FirefoxDriverService firefoxService = new GeckoDriverService.Builder()
					.usingDriverExecutable(new File(firefoxDriverPath)).build();
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			setFirefoxOptions(firefoxOptions);
			findFirefoxHeadless(firefoxOptions);
			driver = new FirefoxDriver(firefoxService, firefoxOptions);
			break;
		default:
			fail("No such browser in the system.");
			break;
		}
		if (driver != null)
			System.out.println(driver.toString().replaceAll("[(].*[)]", ""));
		configDriver(driver);
		return driver;
	}

	/**
	 * Set specific conditions of <Chrome> for this application
	 */
	private static void setChromeOptions(ChromeOptions options) {
		options.addArguments("--no-sandbox");
		if (ConfigReader.getBooleanValue("config", "incognito"))
			options.addArguments("--incognito");
		useChromeForTest(options);
	}

	private static void useChromeForTest(ChromeOptions options) {
		String testChromeUserDataPath = ConfigReader.getTextValue("config", "testChromeUserDataPath");
		if (testChromeUserDataPath != null) {
			options.addArguments(String.format("--user-data-dir=%s", testChromeUserDataPath));
			options.addArguments(
					String.format("--profile-directory=%s", ConfigReader.getTextValue("config", "testChromeProfile")));
		}
		options.setBinary(ConfigReader.getTextValue("config", "testChromeBinPath"));
	}

	private static void findChromeHeadless(ChromeOptions options) {
		if (headless) {
			options.addArguments("--headless=new");
			options.addArguments("--user-agent=" + ConfigReader.getTextValue("config", "chromeUserAgent"));
		}
	}

	/**
	 * Change web view from desktop to either tablet or phone
	 */
	private static void emulateChromeIfMobile(ChromeOptions chromeOptions) {
		if (deviceName != null) {
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
		String firefoxBinPath = ConfigReader.getTextValue("config", "firefoxBinPath");
		if (firefoxBinPath != null)
			firefoxOptions.setBinary(firefoxBinPath);
	}

	private static void useFirefoxProfile(FirefoxOptions firefoxOptions) {
		String firefoxProfilePath = ConfigReader.getTextValue("config", "firefoxProfilePath");
		if (firefoxProfilePath != null)
			firefoxOptions.addArguments("-profile", ConfigReader.getTextValue("config", firefoxProfilePath));
	}

	private static void findFirefoxHeadless(FirefoxOptions options) {
		if (headless)
			options.addArguments("-headless");
	}

	/**
	 * Local drivers location
	 */
	private static String getDriverDir() {
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
