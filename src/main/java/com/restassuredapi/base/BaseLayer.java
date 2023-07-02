package com.restassuredapi.base;

import java.util.Properties;

import com.restassuredapi.util.ReadConfigFile;

import io.restassured.RestAssured;


public class BaseLayer {
	
	public static void launchBaseURI() {
		Properties properties = ReadConfigFile.getProperty("config");
		String baseURI = properties.get("baseURI").toString();
		RestAssured.baseURI = baseURI;
	}	
}
