package utils;

import pages.GoogleAccountPage;
import pages.HomePage;
import pages.NavBar;
import pages.SignInPage;

public class PageManager {

	private static ThreadLocal<PageManager> localPageManager;
	private SignInPage signInPage;
	private GoogleAccountPage googleAccountPage;
	private NavBar navBar;
	private HomePage homePage;

	private PageManager() {
		// WARN: Nothing should be written here.
	}

	public static synchronized PageManager getInstance() {
		if (localPageManager == null)
			localPageManager = new ThreadLocal<PageManager>();
		if (localPageManager.get() == null)
			localPageManager.set(new PageManager());
		return localPageManager.get();
	}

	public static void reset() {
		if (localPageManager.get() != null)
			localPageManager.set(null);
	}

	public SignInPage signInPage() {
		if (signInPage == null)
			signInPage = new SignInPage();
		return signInPage;
	}

	public GoogleAccountPage googleAccountPage() {
		if (googleAccountPage == null)
			googleAccountPage = new GoogleAccountPage();
		return googleAccountPage;
	}

	public NavBar navBar() {
		if (navBar == null)
			navBar = new NavBar();
		return navBar;
	}

	public HomePage homePage() {
		if (homePage == null)
			homePage = new HomePage();
		return homePage;
	}

}
