import java.util.List;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;

public class ComplexJson {

	public static void main(String[] args) {
		JsonPath js = new JsonPath(Payload.courseDetails());
		
		int count = js.getInt("courses.size()");
		System.out.println(count);
		
	     int totalPurchaseAmount = js.getInt("dashboard.purchaseAmount");
	        System.out.println("Total Purchase: " + totalPurchaseAmount);
	        
	   
		
		String getCourseTitle = js.getString("courses.title[0]");
		System.out.println(getCourseTitle);
		
		
		
		for(int i=0;i<count;i++) {
			System.out.println(js.getString("courses["+i+"].title") + ":" + (js.getInt("courses["+i+"].price")));
			
		}
		
		List<String> courseTitle = js.getList("courses.title");
		System.out.println(courseTitle);
		
		for(int i=0;i<count;i++) {
		
			if(courseTitle.get(i).equalsIgnoreCase("RPA")) {
				
			String title= 	 js.get("courses["+i+"].title");
			int price = js.get("courses["+i+"].copies");
			System.out.println( title+ ":" + price);
			}
		}
		
		
		int totalAmt = 0;
		int sum =0;
		
		for(int i=0;i<count;i++) {
			int copiesCount = js.getInt("courses["+i+"].copies");
			int pric= js.getInt("courses["+i+"].price");
			
			totalAmt= copiesCount*pric;
			sum = sum+totalAmt;
			
		}
		System.out.println(sum);
		Assert.assertEquals(sum, totalPurchaseAmount);

	}

}
