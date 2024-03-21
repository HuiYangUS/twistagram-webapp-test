package utils;

public class PageManager {

	private static ThreadLocal<PageManager> localManager;

	private PageManager() {
	}

	public static synchronized PageManager getInstance() {
		if (localManager == null)
			localManager = new ThreadLocal<PageManager>();
		if (localManager.get() == null)
			localManager.set(new PageManager());
		return localManager.get();
	}

	public static void reset() {
		if (localManager.get() != null)
			localManager.set(null);
		tearDown();
	}

	private static void tearDown() {
	}

}
