package ui.base.configs;

import java.util.Optional;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import utils.AppTestUtils;
import utils.ConfigReader;
import utils.DataManager;

public class SimpleReportExtension
		implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback, AfterEachCallback {

	static ExtentSparkReporter reporter;
	static ExtentReports report;
	static ExtentTest test;

	@Override
	public void beforeAll(ExtensionContext context) throws Exception {
		Optional<String> browserData = context.getConfigurationParameter("browser");
		String browserName = "Chrome";
		if (browserData.isPresent()) {
			switch (browserData.get().toLowerCase().strip()) {
			case "firefox":
				browserName = "Firefox";
				break;
			case "edge":
				browserName = "Edge";
				break;
			case "safari":
				browserName = "Safari";
				break;
			default:
				break;
			}
		}

		report = new ExtentReports();
		reporter = new ExtentSparkReporter(AppTestUtils.getCurrentDir() + String.format(
				"/target/extent-reports/report-%s-%s.html", AppTestUtils.getDateString(), AppTestUtils.getTimeStamp()));
		report.attachReporter(reporter);

		reporter.config().setOfflineMode(true);
		reporter.config().setDocumentTitle("Test in " + browserName);
		reporter.config().setReportName("Simple Test Report");
		reporter.config().setTheme(Theme.STANDARD);
		reporter.config().setEncoding("UTF-8");
	}

	@Override
	public void beforeEach(ExtensionContext context) throws Exception {
		String testName = context.getDisplayName().replaceAll("[(].*[)]", "");
		test = report.createTest(testName);
		test.assignAuthor(ConfigReader.getValue("config", "fullName"));
		test.assignDevice(System.getProperty("os.name"));
	}

	@Override
	public void afterEach(ExtensionContext context) throws Exception {
		Optional<Throwable> data = context.getExecutionException();
		if (data.isEmpty())
			test.log(Status.PASS, "Test Passed");
		else {
			test.fail(data.get());
			test.log(Status.FAIL, "Test Failed");
			if (Boolean.valueOf(ConfigReader.getValue("config", "screenshot")))
				DataManager.getWebUtils().savesScreenshot();
		}
	}

	@Override
	public void afterAll(ExtensionContext context) throws Exception {
		report.flush();
	}

}
