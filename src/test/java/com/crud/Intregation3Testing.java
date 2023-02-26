package com.crud;

import org.hamcrest.Matchers;
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

public class Intregation3Testing extends Base
{
	String token;
	 @Test(groups = "integration3")
	 @Owner("Trina")
	 @Description("get an id from all id -update that id-verify get by id")
	 public void getId(ITestContext itestcontext) 
	 {
		 request.basePath(APIConstants.get_path);
		 Response response = RestAssured.given().spec(request)
	                .when().get();
		ValidatableResponse validatableResponse = response.then();
	    ResponseBody response1=validatableResponse.extract().response();
	    String responsebody=response1.asString();
		
		 JsonPath js=new JsonPath(responsebody);
		 int count=js.getInt("bookinid.size()");
		 System.out.println("count:"+count);
		 int bookingid=0;
		 for(int i=0;i<count;i++) {
			  bookingid=js.getInt("bookingid[0]");
			}
		 validatableResponse.statusCode(200);
		 System.out.println("userid:"+bookingid);
		 String bookid=String.valueOf(bookingid);
	     itestcontext.setAttribute("bookingid", bookid);
		 }
	 @Test(groups="integration3",dependsOnMethods="getId")
	 		
	 public void getBookingbyid(ITestContext iTestContext){
		 System.out.println("start get method");
		 token = getToken();
	     String bookingId =  (String) iTestContext.getAttribute("bookingid");
	     //request.contentType(ContentType.JSON);
	        request.basePath(APIConstants.get_path + "/" + bookingId);
	        Response response = RestAssured.given().spec(request)
	                .when().get();
	         response.then().log().all();
	        
	      
	 }
	 
	 @Test(groups="integration3",dependsOnMethods="getBookingbyid")
	 public void updateBooking(ITestContext itestcontext) {
		 System.out.println("start update method");
		 token = getToken();
	        System.out.println(token);
	        String bookingId =  (String) itestcontext.getAttribute("bookingid");
	        System.out.println("Trina - " + bookingId);
	       // request.contentType(ContentType.JSON);
	        request.basePath(APIConstants.update_path + "/" + bookingId);
	        Response response = RestAssured.given().spec(request).cookie("token",token)
	                .when().body(PayLoadManager.updatePayload()).put();
	        ValidatableResponse validatableResponse = response.then().log().all();
	        validatableResponse.body("firstname", Matchers.is("Lucky"));
    }
	 @Test(groups="integration3",dependsOnMethods="updateBooking")
		
	 public void getVerifyid(ITestContext iTestContext){
		 System.out.println("start getverify method");
		 token = getToken();
	     String bookingId =  (String) iTestContext.getAttribute("bookingid");
	     //request.contentType(ContentType.JSON);
	        request.basePath(APIConstants.get_path + "/" + bookingId);
	        Response response = RestAssured.given().spec(request)
	                .when().get();
	        ValidatableResponse validatableResponse =response.then().log().all();
	        validatableResponse.body("firstname", Matchers.is("Lucky"));
	        validatableResponse.body("lastname", Matchers.is("Dutta"));
	          
	 }
	 
		 
		 
	 }
	 
	 


