package com.restassuredapi.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.restassuredapi.util.TestUtil;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class LoginTest {

	@DataProvider
	public Object[][] getBadTestDataForLogin(){
		Object[][] testdata  = TestUtil.readTestData("loginwithbadcred");
		return testdata;
	}
	
	@Test(dataProvider = "getBadTestDataForLogin")
	public void verifyLoginUserNotExist(String email, String password) {
		
		String requestBody = "{\r\n"
				+ "    \"email\": \""+email+"\",\r\n"
				+ "    \"password\": \""+password+"\"\r\n"
				+ "}";
		
		Response response =  RestAssured
				.given()
					.contentType(ContentType.JSON)
					.body(requestBody)
				.when()
					.post("https://reqres.in/api/login");

		response.then()
			.log().all();

		String resError =  response.getBody().jsonPath().getString("error");
		
	Assert.assertEquals(response.getStatusCode(), 400, "Response Code not as expected.");
	Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8", "Response Header Content Type not as expected.");
	Assert.assertEquals(resError, "user not found", "Response Error not as expected.");
		
	}	
}
