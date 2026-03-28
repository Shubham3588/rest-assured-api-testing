package ecommerce;

import org.testng.annotations.Test;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class shop {

	@Test

	public static void placeOrder() {

		RequestSpecification reqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Content-Type", "application/json").build();

		ResponseSpecification resSpec = new ResponseSpecBuilder().expectStatusCode(200).build();

		LoginRequest lr = new LoginRequest();
		lr.setUserEmail("test3588@gmail.com");
		lr.setUserPassword("Test@2025");

		RequestSpecification reqLogin = given().spec(reqSpec).body(lr);

		LoginResponse loginresponse = reqLogin.when().post("/api/ecom/auth/login").then().extract().response()
				.as(LoginResponse.class);
		String token = loginresponse.getToken();
		String id = loginresponse.getUserId();
		System.out.println(token);

		RequestSpecification addProducts = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();

		RequestSpecification requestAddProducts = given().log().all().spec(addProducts).param("productName", "MacBook")
				.param("productAddedBy", id).param("productCategory", "Electronics")
				.param("productSubCategory", "Laptop").param("productPrice", "11500")
				.param("productDescription", "Macbook Lite").param("productFor", "Student")
				.multiPart("productImage", new File("C:\\Users\\acer\\Downloads\\mac.jpg"));

		String addProductRespone = requestAddProducts.when().post("/api/ecom/product/add-product").then().log().all()
				.extract().response().asString();

		JsonPath js = new JsonPath(addProductRespone);
		String productId = js.get("productId");

		RequestSpecification orderProduct = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Content-Type", "application/json").addHeader("Authorization", token).build();

		CreateOrderDetails cod = new CreateOrderDetails();
		cod.setCountry("India");
		cod.setProductOrderedId(productId);
		CreateOrder order = new CreateOrder();

		List<CreateOrderDetails> orderdetail = new ArrayList();
		orderdetail.add(cod);
		order.setOrders(orderdetail);

		RequestSpecification createOrderReq = given().log().all().spec(orderProduct).body(order);

		String resOrder = createOrderReq.when().post("/api/ecom/order/create-order").then().log().all().extract()
				.response().asString();
		System.out.println(resOrder);

		RequestSpecification deleteReqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).addPathParam("productid", productId).build();
		
		RequestSpecification deleteOrder = given().log().all().spec(deleteReqSpec);
		deleteOrder.when().delete("/api/ecom/product/delete-product/{productid}")
		.then().log().all()
		.assertThat()
		.statusCode(200)
		.extract()
		.response()
		.asString();
		

	}

}
