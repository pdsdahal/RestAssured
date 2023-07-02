package com.restassuredapi.util;

import java.io.FileReader;
import java.util.Properties;

public class ReadConfigFile {

	public static Properties getProperty(String propertyFileName) {

		Properties properties = new Properties();
		String baseDirectoryProject = System.getProperty("user.dir");
		String filePath = baseDirectoryProject + "\\src\\test\\resources\\" + propertyFileName + ".properties";

		try {
			FileReader fileReader = new FileReader(filePath);
			properties.load(fileReader);

		} catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
		return properties;
	}
}
