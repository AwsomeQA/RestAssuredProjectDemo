package com.crud;



import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.constant.APIConstants;
import com.payloadmanager.PayLoadManager;
import com.tests.Base;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;

public class Intregation2Testing extends Base {
	;
	String token;
	
	@Test(groups = "integration2")
	@Owner("Trina")
	@Description("Verify that the Booking can be Created and deleted")
	public void CreateBooking(ITestContext iTestContext)
	{
		
		request.basePath(APIConstants.create_path);
        Response response = RestAssured.given().spec(request)
                .when().body(PayLoadManager.createPayload()).post();
        assertaction.verifyStatusCode(response);
        ValidatableResponse validatableResponse = response.then().log().all();
        jsonpath = JsonPath.from(response.asString());
        System.out.println("Booking Id :" + jsonpath.getString("bookingid"));
      
        iTestContext.setAttribute("bookingid", jsonpath.getString("bookingid"));
	}
	@Test(groups="integration2",dependsOnMethods="CreateBooking")
	public void deleteBooking(ITestContext iTestContext) {
		 System.out.println("start delete method");
		 token = getToken();
	     String bookingId = (String) iTestContext.getAttribute("bookingid");
	     //request.contentType(ContentType.JSON);
	        request.basePath(APIConstants.get_path + "/" + bookingId);
	        Response response = RestAssured.given().spec(request).cookie("token",token)
	                .when().delete();
	        assertaction.verifyStatusCode(response);
	        assertaction.verifyStatusLine(response);
	        
	        ValidatableResponse validatableResponse = response.then().log().all();
	     }
	@Test(groups="integration2",dependsOnMethods="deleteBooking")
	public void verifyDeleteId(ITestContext iTestContext) {
		 System.out.println("start get method");
		 token = getToken();
	     String bookingId = (String) iTestContext.getAttribute("bookingid");
	     //request.contentType(ContentType.JSON);
	        request.basePath(APIConstants.get_path + "/" + bookingId);
	        Response response = RestAssured.given().spec(request)
	                .when().get();
	
	        ValidatableResponse validatableResponse = response.then().log().all().assertThat().statusCode(404);
	       
	        ResponseBody response1=validatableResponse.extract().response();
	        String responsebody=response1.asString();
	        Assert.assertTrue(responsebody.contains("Not Found"));
	       
	}

}
