package utils;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {

	private static final String DIR_PATH = "src/test/resources/configs/";

	/**
	 * Return <String> instance
	 */
	public static String getTextValue(String fileName, String key) {
		String filePath = DIR_PATH + fileName + ".properties";
		Properties p = load(filePath);
		return p.containsKey(key) ? (p.getProperty(key).isBlank() ? null : p.getProperty(key)) : null;
	}

	private static Properties load(String filePath) {
		Properties p = new Properties();
		try {
			p.load(new FileInputStream(filePath));
		} catch (Exception e) {
			fail("No config file is discovered.");
		}
		return p;
	}

	private static String errorText = "There is no such key in this config file.";

	public static boolean getBooleanValue(String fileName, String key) {
		String target = getTextValue(fileName, key);
		assertNotNull(target, errorText);
		boolean result = false;
		try {
			result = Boolean.valueOf(target.toLowerCase());
		} catch (Exception e) {
			fail("This key does not have a boolean value.");
		}
		return result;
	}

	public static int getIntNumValue(String fileName, String key) {
		String target = getTextValue(fileName, key);
		assertNotNull(target, errorText);
		int result = 0;
		try {
			result = Integer.valueOf(target.toLowerCase());
		} catch (Exception e) {
			fail("This key does not have a integer value.");
		}
		return result;
	}

}
