package cucumber.hooks.configs.headless;

import io.cucumber.java.BeforeAll;

public class HeadlessWebHook {

	@BeforeAll(order = 1)
	public static void setupHeadlessMode() {
		System.setProperty("headless", "true");
	}

}
