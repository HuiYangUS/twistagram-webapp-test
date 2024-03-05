package demo.ui.metadata;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

public class ChromeOnMacTest {

	@Test
	@EnabledOnOs(OS.MAC)
	@EnabledIfSystemProperty(named = "os.arch", matches = "x86_64")
	void intelMacChromeDriverTest() {
		File driverPath = new File("src/test/resources/drivers/mac/intel/chromedriver/chromedriver");
		System.out.println(driverPath.getAbsolutePath());
		ChromeDriverService service = new ChromeDriverService.Builder().usingDriverExecutable(driverPath).build();
		WebDriver driver = new ChromeDriver(service);
		driver.manage().window().maximize();
		System.out.println(driver.toString());
		driver.quit();
	}

	@Test
	@EnabledOnOs(OS.MAC)
	@EnabledIfSystemProperty(named = "os.arch", matches = "aarch64")
	void mchipMacChromeDriverTest() {
		File driverPath = new File("src/test/resources/drivers/mac/m-chip/chromedriver/chromedriver");
		System.out.println(driverPath.getAbsolutePath());
		ChromeDriverService service = new ChromeDriverService.Builder().usingDriverExecutable(driverPath).build();
		WebDriver driver = new ChromeDriver(service);
		driver.manage().window().maximize();
		System.out.println(driver.toString());
		driver.quit();
	}

}
