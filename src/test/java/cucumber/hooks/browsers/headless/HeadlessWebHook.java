package cucumber.hooks.browsers.headless;

import io.cucumber.java.BeforeAll;

public class HeadlessWebHook {

	@BeforeAll(order = 1)
	public static void runChrome() {
		System.setProperty("headless", "true");
	}

}
