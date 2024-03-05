package cucumber.hooks.browsers.firefox;

import io.cucumber.java.BeforeAll;

public class FirefoxWebHook {

	@BeforeAll(order = 1)
	public static void runFirefox() {
		System.setProperty("browser", "firefox");
	}

}
