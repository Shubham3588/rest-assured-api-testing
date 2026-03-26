package serialization;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class SerializeTest {
	
	@Test
	
	public void getLocation()
	{
		
		GetPlaceDetails p = new GetPlaceDetails();
		p.setAccuracy(85);
		p.setAddress("Marathalli, Bangalore");
		p.setLanguage("English");
		p.setName("Shubham");
		p.setPhone_number("+91 65655767868");
		p.setWebsite("www.fb.com");
		
		Location lp = new Location();
		lp.setLat(87.784368);
		lp.setLng(-84.6575);
		p.setLocation(lp);
		
		ArrayList<String> al = new ArrayList<String>();
		al.add("Park");
		al.add("Shop");
		p.setTypes(al);
		
	
	
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
	Response res=	given().log().all().queryParam("key", "qaclick123")
		.body(p)
		.when()
		.post("/maps/api/place/add/json")
		.then()
		.assertThat()
		.statusCode(200)
		.extract()
		.response();
	
	System.out.println(res.asString());
		
	}
}
