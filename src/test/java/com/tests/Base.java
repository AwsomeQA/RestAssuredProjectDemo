package com.tests;

import org.testng.annotations.BeforeMethod;

import com.constant.APIConstants;
import com.payloadmanager.PayLoadManager;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.apache.log4j.PropertyConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class Base {
	public RequestSpecification request;
	public Response response;
	public PayLoadManager payloadmanager;
	public AssertAction assertaction;
	public JsonPath jsonpath;
	@BeforeMethod
	public void setup()
	{
		assertaction=new AssertAction();
		payloadmanager=new PayLoadManager();
		String log4jConfPath = "C:\\eclipse-workspace\\Restfulbooker\\log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);
		request=(RequestSpecification) new RequestSpecBuilder().setBaseUri(APIConstants.BASE_URL)
        .addHeader("Content-Type", "application/json").build().log().all();
	}
	public String getToken() {
		request=RestAssured.given().baseUri(APIConstants.BASE_URL).basePath("/auth");
		String payload = "{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}";
		response=request.header("Content-Type","application/json").body(payload).when().post();
		jsonpath=new JsonPath(response.asPrettyString());
		return jsonpath.getString("token");
		
	}

}
