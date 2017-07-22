package com.jimmy.googleflight.objects;

import java.util.Date;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jimmy.flightcalculator.client.GoogleFlightClient;
import com.jimmy.flightcalculator.client.GoogleFlightRequest;

public class TestRequest {
	@Test
	public void createRequest() throws JsonProcessingException{
		GoogleFlightClient gfg = new GoogleFlightClient();
		GoogleFlightRequest request = new GoogleFlightRequest("DEN", "SEA", new Date(), 1, "30");
		System.out.println(request.createRequestJson());
	}
	
	
}
