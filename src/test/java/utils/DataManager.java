package utils;

import static org.junit.jupiter.api.Assertions.*;

public class DataManager {

	private static ThreadLocal<DataManager> localDataManager;
	private static String password;
	private WebUtils webUtils;

	private DataManager() {
		// Nothing should be written here.
	}

	public static DataManager getInstance() {
		if (localDataManager == null)
			localDataManager = new ThreadLocal<DataManager>();
		if (localDataManager.get() == null)
			localDataManager.set(new DataManager());
		return localDataManager.get();
	}

	public static void reset() {
		if (localDataManager != null && localDataManager.get() != null)
			localDataManager.set(null);
	}

	public String getPasswordValue() {
		assertNotNull(password, "Data Manager does not have the password value.");
		return password;
	}

	public void setPasswordValue(String password) {
		DataManager.password = password;
	}

	public WebUtils webUtils() {
		if (webUtils == null)
			webUtils = new WebUtils(DriverManager.getDriver());
		return webUtils;
	}

}
