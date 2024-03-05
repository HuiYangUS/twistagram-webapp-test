package cucumber.hooks.browsers.edge;

import io.cucumber.java.BeforeAll;

public class EdgeWebHook {

	@BeforeAll(order = 1)
	public static void runEdge() {
		System.setProperty("browser", "edge");
	}

}
