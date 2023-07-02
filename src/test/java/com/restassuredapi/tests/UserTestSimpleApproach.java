package com.restassuredapi.tests;

import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class UserTestSimpleApproach {
	
	String primaryToken = "aab26b574d2b0a3d2368764e6b7992c951207190f181928319856c8164ce0728";
	
	int userId = 0;
	

	
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
			
			RestAssured
				.given()
					.contentType(ContentType.JSON)
					.header("Authorization", "Bearer "+primaryToken)
					.baseUri("https://gorest.co.in/")
					.body(requestBody)
				.when()
					.post("/public/v2/users")
				.then()
					.log().all()
					.assertThat().statusCode(201)
					.assertThat().header("Content-Type", equalTo("application/json; charset=utf-8"))
					.assertThat().body("size()", Matchers.is(5))
					.assertThat().body("name", equalTo(reqName))
					.assertThat().body("email", equalTo(reqEmail))
					.assertThat().body("gender", equalTo(reqGender))
					.assertThat().body("status", equalTo(reqStatus));
	}
	
	
	@Test(priority = 2)
	public void testGetUsers() {
		
		RestAssured
			.given()
				.contentType(ContentType.JSON)
			.when()
				.get("/public/v2/users")
			.then()
				.log().all()
				.assertThat().statusCode(200)
				.assertThat().header("Content-Type", equalTo("application/json; charset=utf-8"))
				.assertThat().body("size()", Matchers.is(5))
				.assertThat().body("[1].name", equalTo(""));		
	}
	
}
