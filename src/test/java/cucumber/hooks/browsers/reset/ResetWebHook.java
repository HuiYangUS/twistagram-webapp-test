package cucumber.hooks.browsers.reset;

import io.cucumber.java.BeforeAll;
import utils.AppTestUtils;

public class ResetWebHook {

	@BeforeAll(order = 2)
	public static void reset() {
		AppTestUtils.testConfigReset();
	}

}
