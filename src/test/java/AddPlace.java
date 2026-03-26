import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

public class AddPlace {

	public static void main(String[] args) {

		// Add Place

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		Response response = given().log().all().queryParam("key", "qaclick123")
				.header("Content-Type", "application/json").body(Payload.addPlace()).when()
				.post("/maps/api/place/add/json").then().log().all().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)").extract().response();
		String placeId = response.jsonPath().getString("place_id");

		// Update Place
		String newAddress = "Bellandur,Bangalore";

		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body("{\r\n" + "\"place_id\":\"" + placeId + "\",\n" + "\"address\":\"" + newAddress + "\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n" + "}\r\n" + "")
				.when().put("/maps/api/place/update/json").then().log().all().assertThat().statusCode(200).extract()
				.response();

		// get place

		Response res = given().log().all().queryParam("place_id", placeId).queryParam("key", "qaclick123").when()
				.get("/maps/api/place/get/json").then().assertThat().statusCode(200).extract().response();
		String actualAddress = res.jsonPath().getString("address");
		System.out.println(actualAddress);

		Assert.assertEquals(actualAddress, newAddress);

		// Delete place

		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").body(placeId)
				.when().delete("/maps/api/place/get/json").then().assertThat().statusCode(200);

	}

}
