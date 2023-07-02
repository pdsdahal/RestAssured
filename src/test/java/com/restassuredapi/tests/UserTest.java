package com.restassuredapi.tests;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;
import com.restassuredapi.base.BaseLayer;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserTest {
	
	String primaryToken = "aab26b574d2b0a3d2368764e6b7992c951207190f181928319856c8164ce0728";
	
	int userId = 0;
	
	@BeforeClass
	public void setUp() {
		BaseLayer.launchBaseURI();
	}
	
	@Test(priority = 1)
	public void testCreateUser() {
		
		Faker faker = new Faker();
		String reqName = faker.name().fullName();
		String reqGender = faker.demographic().sex().toLowerCase();
		String reqEmail = faker.internet().emailAddress();
		String reqStatus = "active";
		
		String requestBody = "{\r\n"
				+ "  \"name\": \""+reqName+"\",\r\n"
				+ "  \"gender\": \""+reqGender+"\",\r\n"
				+ "  \"email\": \""+reqEmail+"\",\r\n"
				+ "  \"status\": \""+reqStatus+"\"\r\n"
				+ "}";
			
		Response response =  RestAssured
						.given()
							.contentType(ContentType.JSON)
							.header("Authorization", "Bearer "+primaryToken)
							.body(requestBody)
						.when()
							.post("/public/v2/users");
		
		response.then()
					.log()
					.all()
					.assertThat().body("size()", Matchers.is(5));
		
		//extract response body
		String resName =  response.getBody().jsonPath().getString("name");
		String resEmail =  response.getBody().jsonPath().getString("email");
		String resGender =  response.getBody().jsonPath().getString("gender");
		String resStatus =  response.getBody().jsonPath().getString("status");
		
		Assert.assertEquals(response.getStatusCode(), 201, "Response Code not as expected.");
		Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8", "Response Header Content Type not as expected.");
		Assert.assertEquals(resName, reqName,"Name not as expected.");
		Assert.assertEquals(resEmail,reqEmail,"Email not as expected.");
		Assert.assertEquals(resGender,reqGender,"Gender not as expected.");
		Assert.assertEquals(resStatus,reqStatus,"Status not as expected.");	
		
		
	}
	
	
	@Test(priority = 2)
	public void testGetUsers() {
		
		Response response = RestAssured
							.given()
								.contentType(ContentType.JSON)
							.when()
								.get("/public/v2/users");
		response.then()
					.log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200, "Response Code not as expected.");
		Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8", "Response Header Content Type not as expected.");
		
		//user https://jsonpathfinder.com/ to extract json path
		String resNameAtIndexOne = response.getBody().jsonPath().getString("[1].name");
		userId = response.getBody().jsonPath().getInt("[1].id");
		
		//for now i am using the same response name as expected name.. but this is not real validation 
		String expName =  response.getBody().jsonPath().getString("[1].name");
				
		Assert.assertEquals(resNameAtIndexOne, expName, "Name not as expected.");
		
	}
	
	@Test(priority = 3, dependsOnMethods = "testGetUsers")
	public void testGetUser() {
		
	Response response = RestAssured
						.given()
							.contentType(ContentType.JSON)
						.when()
							.get("/public/v2/users/"+userId);
							
			response.then()
						.log().all()
						.assertThat().body("size()", Matchers.is(5));
		
		//extract response body
		String resName =  response.getBody().jsonPath().getString("name");
		
		Assert.assertEquals(response.getStatusCode(), 200, "Response Code not as expected.");
		Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8", "Response Header Content Type not as expected.");
		Assert.assertEquals(resName, "Aasa Chattopadhyay", "Name not as expected.");
	}
	
	
	
	@Test(priority = 4, dependsOnMethods = "testGetUsers")
	public void tetsPutUser() {
		
		Faker faker = new Faker();
		String reqName = faker.name().fullName();
		String reqStatus = "active";
		
		String requestBody = "{\r\n"
				+ "  \"name\": \""+reqName+"\",\r\n"
				+ "  \"status\": \""+reqStatus+"\"\r\n"
				+ "}";
		
		Response response =  RestAssured
				.given()
					.contentType(ContentType.JSON)
					.header("Authorization", "Bearer "+primaryToken)
					.body(requestBody)
				.when()
					.put("/public/v2/users/"+userId);
					
		response.then()
				.log().all();
	
	
	String resName =  response.getBody().jsonPath().getString("name");

	Assert.assertEquals(response.getStatusCode(), 200, "Response Code not as expected.");
	Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8", "Response Header Content Type not as expected.");
	Assert.assertEquals(resName, reqName,"Name not as expected.");
	
	}
	
	@Test(priority = 5)
	public void testDeleteUser() {
		
		
		Response response =  RestAssured
				.given()
					.contentType(ContentType.JSON)
					.header("Authorization", "Bearer "+primaryToken)
				.when()
					.delete("/public/v2/users/"+userId);
					
		response.then()
				.log().all();
		
		//verify all the response same as above
	}
	
	
}
