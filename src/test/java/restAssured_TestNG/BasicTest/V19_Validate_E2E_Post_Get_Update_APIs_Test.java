package restAssured_TestNG.BasicTest;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.payload;


public class V19_Validate_E2E_Post_Get_Update_APIs_Test {

	public static void main(String[] args) {
		// Validate if Add Place API is working as expected or not

		// given - all input details
		// when - submit the API - resource, http method
		// then - validate the response
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String postResponse = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(payload.addPlace())
		.when().post("/maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		
		JsonPath js = new JsonPath(postResponse);
		String place_Id = js.getString("place_id");
		System.out.println("The place_Id is: "+place_Id);
		
		//Update Place id and new address
		String newAddress = "70 Summer walk, USA";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+place_Id+"\",\r\n"
				+ "\"address\":\""+ newAddress +"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}\r\n"
				+ "")
		.when().put("/maps/api/place/update/json")
		
		.then().log().all().assertThat().statusCode(200)
		.body("msg", equalTo("Address successfully updated"));
		
		// Check the address is updated for the Place id via Get API
		
		String getResponse = given().log().all().queryParam("key", "qaclick123")
		.queryParam("place_id", place_Id)
		.when().get("/maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js1 = new JsonPath(getResponse);
		String actualAddress = js1.getString("address");
		System.out.println("The actual Address is: " + actualAddress);
		Assert.assertEquals(actualAddress, newAddress);
		System.out.println("The End to End test is completed!");
	}

}
