import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

import java.io.File;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class CreateBugInJira {

	@Test

	public static void createIssue() {

		RestAssured.baseURI = "https://learnshubham3588.atlassian.net";

		String issueResponse = given().log().all().header("Content-Type", "application/json").header("Authorization",
				"Basic bGVhcm5zaHViaGFtMzU4OEBnbWFpbC5jb206QVRBVFQzeEZmR0YwOUgyQmxzRXE4SEJBNmp2NEZVY0NxbWIyT3hONVRvMzBEMGxIaXY5eExibDRSbTg2VGcxS0lmdTF5NHVsanhqN3piSjFFczJWNWczZUhZZ2UxazdNdDRMRE9sV19WdVhiOENtQmpmMnZLZVFqNmdEVkJKcm1TRU9sZTFGVDVoQUswNGgxdFBYT01fR0JjOUZWRFpSUTFKT0FqSmFyMlB5LXNXT211bWszZllVPUM3MjE5M0FG")
				.body("{\r\n" + "    \"fields\": {\r\n" + "       \"project\":\r\n" + "       {\r\n"
						+ "          \"key\": \"QT\"\r\n" + "       },\r\n"
						+ "       \"summary\": \"Dropdowns not visible\",\r\n" + "       \"issuetype\": {\r\n"
						+ "          \"name\": \"Bug\"\r\n" + "       }\r\n" + "   }\r\n" + "}\r\n" + "")
				.when().post("/rest/api/2/issue").then().assertThat().statusCode(201).extract().response().asString();
		System.out.println(issueResponse);

		JsonPath js = ReusableMethods.rawToJson(issueResponse);
		String id = js.get("id");
		System.out.println("Id===========" + id);

		// Add Attachments

		given().log().all().pathParam("key", id).header("X-Atlassian-Token", "no-check").header("Authorization",
				"Basic bGVhcm5zaHViaGFtMzU4OEBnbWFpbC5jb206QVRBVFQzeEZmR0YwOUgyQmxzRXE4SEJBNmp2NEZVY0NxbWIyT3hONVRvMzBEMGxIaXY5eExibDRSbTg2VGcxS0lmdTF5NHVsanhqN3piSjFFczJWNWczZUhZZ2UxazdNdDRMRE9sV19WdVhiOENtQmpmMnZLZVFqNmdEVkJKcm1TRU9sZTFGVDVoQUswNGgxdFBYT01fR0JjOUZWRFpSUTFKT0FqSmFyMlB5LXNXT211bWszZllVPUM3MjE5M0FG")
				.multiPart("file", new File("C:\\Users\\acer\\Downloads\\download.jpeg")).log().all().when()
				.post("rest/api/3/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);

	}

}
