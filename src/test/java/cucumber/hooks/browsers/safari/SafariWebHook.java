package cucumber.hooks.browsers.safari;

import io.cucumber.java.BeforeAll;

public class SafariWebHook {

	@BeforeAll(order = 1)
	public static void runSafari() {
		System.setProperty("browser", "safari");
	}

}
