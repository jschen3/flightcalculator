package com.jimmy.flightcalculator.client;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.jimmy.flightcalculator.objects.Flight;

public class TestResponse {
	String resources = "src/test/java/resources";
	@Test
	public void testParseResponse() throws IOException{
		Date date = new Date();
		File f = new File(resources+"/sampleflightresponse.json");
		List<Flight> flights=GoogleFlightParser.parseResponse(f, "den", "sea", "7-17-2017");
		System.out.println(flights.size());
	}
}
