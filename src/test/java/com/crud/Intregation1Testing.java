package com.crud;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.constant.APIConstants;

import com.payloadmanager.PayLoadManager;
import com.tests.Base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.internal.shadowed.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class Intregation1Testing extends Base {
	String token;
	private static final Logger log = LogManager.getLogger(Intregation1Testing.class);
	

	 @Test(groups = "integration")
	 @Owner("Trina")

	    @Description("Verify that the Booking can be Created")
	    public void testCreateBooking(ITestContext iTestContext) throws JsonProcessingException {
	        request.basePath(APIConstants.create_path);
	        Response response = RestAssured.given().spec(request)
	                .when().body(PayLoadManager.createPayload()).post();
	        ValidatableResponse validatableResponse = response.then().log().all();
	        jsonpath = JsonPath.from(response.asString());
	        System.out.println("Booking Id :" + jsonpath.getString("bookingid"));
	        validatableResponse.statusCode(200);
	        iTestContext.setAttribute("bookingid", jsonpath.getString("bookingid"));
	    }
	 @Test(groups = "integration", dependsOnMethods = "testCreateBooking")
	    public void testCreateAndUpdateBooking(ITestContext iTestContext) throws JsonProcessingException {
	        token = getToken();
	        System.out.println(token);
	        String bookingId = (String) iTestContext.getAttribute("bookingid");
	        System.out.println("Trina - " + bookingId);
	        request.contentType(ContentType.JSON);
	        request.basePath(APIConstants.update_path + "/" + bookingId);
	        Response response = RestAssured.given().spec(request).cookie("token",token)
	                .when().body(PayLoadManager.updatePayload()).put();
	        ValidatableResponse validatableResponse = response.then().log().all();
	        validatableResponse.body("firstname", Matchers.is("Lucky"));
	        

	    }
	
	 @Test(groups="integration",dependsOnMethods = "testCreateAndUpdateBooking")
	 public void getBookinfbyid(ITestContext iTestContext){
		 System.out.println("start updated method");
		 token = getToken();
	     String bookingId = (String) iTestContext.getAttribute("bookingid");
	     request.contentType(ContentType.JSON);
	        request.basePath(APIConstants.get_path + "/" + bookingId);
	        Response response = RestAssured.given().spec(request)
	                .when().get();
	        ValidatableResponse validatableResponse = response.then().log().all();
	        validatableResponse.body("firstname", Matchers.is("Lucky"));
	        validatableResponse.body("lastname", Matchers.is("Dutta"));
	        log.error("TestDone");
	      
	 }

}
