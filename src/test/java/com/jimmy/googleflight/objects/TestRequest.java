package com.jimmy.googleflight.objects;

import java.util.Date;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jimmy.flightcalculator.client.GoogleFlightClient;
import com.jimmy.flightcalculator.googleflight.objects.Request;

public class TestRequest {
	@Test
	public void createRequest() throws JsonProcessingException{
		GoogleFlightClient gfg = new GoogleFlightClient();
		Request request = new Request("DEN", "SEA", new Date(), 1, "30");
		System.out.println(request.createRequestJson());
	}
	
	
}
