package utils;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class WebUtils {

	private WebDriver driver;
	private Actions actions;
	private JavascriptExecutor js;

	public WebUtils(WebDriver driver) {
		this.driver = driver;
		actions = new Actions(driver);
		js = (JavascriptExecutor) driver;
	}

	public void jsClick(WebElement element) {
		js.executeScript("arguments[0].click();", element);
	}

	public void jsClick(By locator) {
		jsClick(driver.findElement(locator));
	}

	public void focusElement(WebElement element) {
		// set element border to red and thick
		js.executeScript("arguments[0].style.borderColor = 'red'; arguments[0].style.borderWidth = 'thick';", element);
		AppTestUtils.pause(2);
		// reset element border
		js.executeScript("arguments[0].style.borderColor = ''; arguments[0].style.borderWidth = '';", element);
		AppTestUtils.pause(1);
	}

	public void focusElement(By locator) {
		focusElement(driver.findElement(locator));
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
