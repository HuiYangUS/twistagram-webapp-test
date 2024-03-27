package api;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import utils.ConfigReader;

public class VercelDemoTest {

	@Test
	@DisplayName("Vercel Demo API Test")
	void runTest() {
		RestAssured.baseURI = "https://api.vercel.com";
		String endpoint = "/v2/user";
		String headerValue = "Bearer " + ConfigReader.getTextValue("vercel", "token");
		Response responseData = RestAssured.given().header(new Header("Authorization", headerValue)).when()
				.get(endpoint);
		assertEquals(200, responseData.getStatusCode());
		String name = responseData.getBody().jsonPath().get("user.name");
		System.out.println("Name: " + name + "\n");
		responseData.prettyPrint();
	}

}
