package demo.ui.base.config;

import java.util.Optional;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class WebTestConfig implements BeforeEachCallback, AfterEachCallback {

	@Override
	public void beforeEach(ExtensionContext context) throws Exception {
		Optional<String> browserData = context.getConfigurationParameter("browser");
		if (browserData.isPresent())
			System.setProperty("browser", browserData.get());
		Optional<String> autoData = context.getConfigurationParameter("auto");
		if (autoData.isPresent())
			System.setProperty("auto", autoData.get());
		Optional<String> headlessData = context.getConfigurationParameter("headless");
		if (headlessData.isPresent())
			System.setProperty("headless", headlessData.get());
	}

	@Override
	public void afterEach(ExtensionContext context) throws Exception {
		String testName = context.getDisplayName().replaceAll("[(].*[)]", "");
		if (context.getExecutionException().isPresent())
			System.out.println(testName + " has failed.");
		else
			System.out.println(testName + " passed.");
		System.out.println();
	}

}
