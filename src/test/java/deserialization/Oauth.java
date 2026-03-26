package deserialization;
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
		
		JsonPath js =new JsonPath(res);
		String token = js.get("access_token");
		System.out.println("access_token" + token);
		
		
		//getCourseDetails
		
		GetCourse gc=	given().log().all().queryParam("access_token", token)
		.when()
		.get("/oauthapi/getCourseDetails")
		.then()
		.log().all()
		.extract()
		.response().as(GetCourse.class);
	
	System.out.println(gc.getLinkedIn());
	
	int len =gc.getCourses().getApi().size();
	
	for(int i=0;i<len;i++) {
		if(gc.getCourses().getApi().get(i).getCourseTitle().equalsIgnoreCase("Rest Assured Automation using Java")) {
			int price = gc.getCourses().getApi().get(i).getPrice();
			System.out.println("Price========"+price);
			break;
		}
	}
	
	int webautolen = gc.getCourses().getWebAutomation().size();
	
	for(int i=0;i<webautolen;i++) {
		String title = gc.getCourses().getWebAutomation().get(i).getCourseTitle();
		System.out.println(title);
	}
	
	}

}
