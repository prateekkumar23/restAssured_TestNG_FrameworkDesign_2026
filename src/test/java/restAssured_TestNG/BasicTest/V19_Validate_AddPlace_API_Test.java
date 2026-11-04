package restAssured_TestNG.BasicTest;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import files.payload;


public class V19_Validate_AddPlace_API_Test {

	public static void main(String[] args) {
		// Validate if Add Place API is working as expected or not

		// given - all input details
		// when - submit the API - resource, http method
		// then - validate the response
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(payload.addPlace())
		.when().post("/maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("Server", "Apache/2.4.52 (Ubuntu)");
	}

}
