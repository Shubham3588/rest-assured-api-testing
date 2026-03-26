import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {
	
	@Test(dataProvider="BookData")
	
	public void addBook(String isbn,String aisle) {
		
		RestAssured.baseURI="http://216.10.245.166";
		
	String response = 	given().log().all().header("Content-Type", "application/json")
		.body(Payload.addBook(isbn,aisle))
		.when()
		.post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200)
		.extract()
		.response()
		.asString();
		System.out.println("Response:"+response);
		JsonPath js = ReusableMethods.rawToJson(response);
		String id = js.get("ID");
		System.out.println("id=========="+id);
	
	}
	
	@DataProvider(name="BookData")
	public Object[][] getData() {
		return new Object[][] {{"tyui","9208"},{"hkgj","5452"},{"dfgh","2367"}};
	}

}
