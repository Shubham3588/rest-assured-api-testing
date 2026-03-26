import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Oauth {
	
	@Test
	
	public static void getCourseDetails() {
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		String res = given().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.formParam("grant_type", "client_credentials")
		.formParam("scope", "trust")
		.when()
		.post("/oauthapi/oauth2/resourceOwner/token")
		.then()
		.log().all()
		.assertThat().statusCode(200)
		.extract().response().asString();
		System.out.println(res);
		
		JsonPath js = ReusableMethods.rawToJson(res);
		String token = js.get("access_token");
		System.out.println("access_token" + token);
		
		
		//getCourseDetails
		
	String details=	given().log().all().queryParam("access_token", token)
		.when()
		.get("/oauthapi/getCourseDetails")
		.then()
		.log().all()
		.extract()
		.response().asString();
	
	System.out.println(details);
	}

}
