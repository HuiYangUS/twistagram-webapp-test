package utils;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebUtils {

	private WebDriver driver;
	private WebDriverWait wait;
	private Actions actions;
	private JavascriptExecutor js;

	public WebUtils(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(Long.valueOf(ConfigReader.getValue("config", "waitTime"))));
		actions = new Actions(driver);
		js = (JavascriptExecutor) driver;
	}

	public void jsClick(WebElement element) {
		js.executeScript("arguments[0].click();", element);
	}

	public void jsClick(By locator) {
		jsClick(driver.findElement(locator));
	}

	/**
	 * Change border of an element to be thick and red
	 */
	public void elementOnFocus(WebElement element) {
		// set element border to red and thick
		js.executeScript("arguments[0].style.borderColor = 'red'; arguments[0].style.borderWidth = 'thick';", element);
		AppTestUtils.pause(2);
		// reset element border
		js.executeScript("arguments[0].style.borderColor = ''; arguments[0].style.borderWidth = '';", element);
		AppTestUtils.pause(1);
	}

	public void elementOnFocus(By locator) {
		elementOnFocus(driver.findElement(locator));
	}

	public Actions mouse() {
		return actions;
	}

	public Actions keyboard() {
		return actions;
	}

	public void savesScreenshot() {
		savesScreenshot(null, false);
	}

	/**
	 * If the user is logged in, log out.
	 */
	public void appLogOut() {
		try {
			driver.findElement(By.linkText("Sign out")).click();
			driver.findElement(By.id("submitButton")).click();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * This method is locked out at this moment.
	 */
	public void testEnvLogin() {
		// remove the line below when the problem is fixed
		assertTrue(false, "This method is locked out at this moment.");
		wait.until(ExpectedConditions.urlContains(ConfigReader.getValue("test-env", "url")));
		String originWindow = driver.getWindowHandle();
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(ConfigReader.getValue("test-env", "id"))))
				.click();
		wait.until(ExpectedConditions.numberOfWindowsToBe(2));
		Set<String> windows = driver.getWindowHandles();
		String targetWindow = null;
		for (String window : windows) {
			if (!window.equals(originWindow)) {
				targetWindow = window;
				break;
			}
		}
		driver.switchTo().window(targetWindow);
		driver.findElement(By.name("login")).sendKeys(ConfigReader.getValue("test-env", "username"));
		driver.findElement(By.name("password")).sendKeys(ConfigReader.getValue("test-env", "password"));
		driver.findElement(By.name("commit")).submit();
		wait.until(ExpectedConditions.numberOfWindowsToBe(1));
		driver.switchTo().window(originWindow);
		wait.until(ExpectedConditions.urlContains(ConfigReader.getValue("config", "url")));
	}

	/**
	 * Takes a screenshot and stores in the "target" folder
	 */
	public void savesScreenshot(String postfix, boolean useTimeStamp) {
		String tail = "-" + postfix;
		if (postfix == null)
			tail = "";
		if (useTimeStamp)
			tail += "-" + AppTestUtils.getDateString() + "-" + AppTestUtils.getTimeStamp();
		TakesScreenshot cam = (TakesScreenshot) driver;
		File imgData = cam.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(imgData, new File(String.format("target/webpage-screenshots/screenshot%s.png", tail)));
		} catch (IOException e) {
			assertTrue(false, "Failed to capture the screenshot.");
		}
	}

}
