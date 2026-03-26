import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class ReadingFileFromOtherFile {

	@Test
	public void addPlace() throws IOException {

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		Response response = given().log().all().queryParam("key", "qaclick123")
				.header("Content-Type", "application/json")
				.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\acer\\Downloads\\addPlace.json")))).when()
				.post("/maps/api/place/add/json").then().log().all().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)").extract().response();
		String placeId = response.jsonPath().getString("place_id");

	}
}
