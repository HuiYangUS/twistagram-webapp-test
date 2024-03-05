package cucumber.hooks.browsers.reset;

import io.cucumber.java.BeforeAll;

public class ResetWebHook {

	@BeforeAll(order = 2)
	public static void reset() {
		System.clearProperty("browser");
		System.clearProperty("headless");
	}

}
