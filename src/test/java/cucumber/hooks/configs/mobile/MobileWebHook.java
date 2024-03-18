package cucumber.hooks.configs.mobile;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class MobileWebHook {

	private static String phoneName = "iPhone SE";
	private static String tabletName = "iPad Mini";

	@Before(order = 1, value = "@mobile or @phone or @tablet")
	public void setUp() {
		setupMobileTest();
	}

	private static void setupMobileTest() {
		System.setProperty("mobile", "true");
	}

	@Before(order = 1, value = "@phone")
	public void setupPhone() {
		System.setProperty("deviceName", phoneName);
	}

	@Before(order = 1, value = "@tablet")
	public void setupTablet() {
		System.setProperty("deviceName", tabletName);
	}

	@After(order = 1, value = "@phone or @tablet")
	public void tearDown() {
		System.clearProperty("mobile");
	}

}
