package com.payloadmanager;

import com.google.gson.Gson;
import com.payload.Booking;
import com.payload.Bookingdates;


public class PayLoadManager {
	
	public static String createPayload() {
		Booking booking=new Booking();
		booking.setFirstname("Trina");
		booking.setLastname("Sinha");
		booking.setTotalprice(1500);
		booking.setDepositpaid(true);
		booking.setAdditionalneeds("Lunch");
		Bookingdates bookingdates=new Bookingdates();
		bookingdates.setCheckin("2023-2-20");
		bookingdates.setCheckout("2023-2-22");
		booking.setBookingdates(bookingdates);
		Gson gson=new Gson();
		String payload=gson.toJson(booking);
		return payload;
	}
	
	public static String updatePayload() {
		Booking booking=new Booking();
		booking.setFirstname("Lucky");
		booking.setLastname("Dutta");
		booking.setTotalprice(1600);
		booking.setDepositpaid(true);
		booking.setAdditionalneeds("Lunch");
		Bookingdates bookingdates=new Bookingdates();
		bookingdates.setCheckin("2023-1-20");
		bookingdates.setCheckout("2023-1-22");
		booking.setBookingdates(bookingdates);
		Gson gson=new Gson();
		String payload=gson.toJson(booking);
		return payload;
	}
	public String updatedPayload() {
		return null;
		
	}

}
