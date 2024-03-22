package cucumber.hooks.web;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.AppTestUtils;
import utils.DataManager;

public class TestWebHookAPI {

	@Before(order = 2, value = "@api")
	public void setUp() {
		DataManager.getInstance();
	}

	@After(order = 2, value = "@api")
	public void tearDown(Scenario scenario) {
		DataManager.reset();
		AppTestUtils.testConfigReset();
	}

}
