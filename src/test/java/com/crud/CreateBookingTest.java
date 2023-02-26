package com.crud;

import org.testng.annotations.Test;

import com.constant.APIConstants;
import com.payloadmanager.PayLoadManager;
import com.tests.Base;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.internal.shadowed.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class CreateBookingTest extends Base {
	String token;
	
	 @Test
	    @Owner("Promode")
	    @Severity(SeverityLevel.NORMAL)
	    @Description("Verify that the Booking can be Created")
	    public void testCreateBooking() throws JsonProcessingException {
	        request.basePath(APIConstants.create_path);

	        Response response = RestAssured.given().spec(request)
	                .when().body(PayLoadManager.createPayload()).post();
	        ValidatableResponse validatableResponse = response.then().log().all();
	        jsonpath = JsonPath.from(response.asString());
	        System.out.println("Booking Id :" + jsonpath.getString("bookingid"));
	        validatableResponse.statusCode(200);
	       

	    }


}
