package demo.ui.base.web;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import demo.ui.base.config.SimpleReportExtension;
import demo.ui.base.config.WebTestConfig;
import utilities.DriverManager;
import utilities.PageManager;
import utilities.WebUtils;

@ExtendWith({ WebTestConfig.class, SimpleReportExtension.class })
public class DriverFactoryWebBase {

	protected static WebDriver driver;
	protected static WebDriverWait wait;
	protected static PageManager pages;
	protected static WebUtils webUtils;

	@BeforeEach
	protected void setUp() {
		driver = DriverManager.getDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		pages = PageManager.getInstance();
		webUtils = new WebUtils(driver);
	}

	@AfterEach
	protected void tearDown() {
		DriverManager.reset();
		PageManager.reset();
	}

}
