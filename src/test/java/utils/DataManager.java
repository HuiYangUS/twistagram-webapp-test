package utils;

import pojos.ui.Profile;

public class DataManager {

	private static ThreadLocal<DataManager> localDataManager;
	private Profile profile;
	private WebUtils webUtils;

	private DataManager() {
		// WARN: Nothing should be written here.
	}

	public static DataManager getInstance() {
		if (localDataManager == null)
			localDataManager = new ThreadLocal<DataManager>();
		if (localDataManager.get() == null)
			localDataManager.set(new DataManager());
		return localDataManager.get();
	}

	public static void reset() {
		// localDataManager cannot be null
		if (localDataManager != null && localDataManager.get() != null)
			localDataManager.remove();
	}

	public WebUtils webUtils() {
		if (webUtils == null)
			webUtils = new WebUtils(DriverManager.getDriver());
		return webUtils;
	}

	public Profile getProfile() {
		if (profile == null)
			profile = new Profile();
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

}
