package cucumber.hooks.browsers.chrome;

import io.cucumber.java.BeforeAll;

public class ChromeWebHook {

	@BeforeAll(order = 1)
	public static void runChrome() {
		System.setProperty("browser", "chrome");
	}

}
