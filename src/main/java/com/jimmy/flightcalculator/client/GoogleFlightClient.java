package com.jimmy.flightcalculator.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jimmy.flightcalculator.main.FlightCalculator;
import com.jimmy.flightcalculator.objects.Flight;
import com.jimmy.flightcalculator.objects.FlightSegment;

public class GoogleFlightClient {
	private final String GOOGLE_FLIGHT_URL = "https://www.googleapis.com/qpxExpress/v1/trips/search?key=";

	public String callGoogleFlights(GoogleFlightRequest request, String apiKey) throws IOException {
		String urlToCall = GOOGLE_FLIGHT_URL + apiKey;
		URL url = new URL(urlToCall);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		String requestString = request.createRequestJson();
		OutputStream os = conn.getOutputStream();
		os.write(requestString.getBytes());
		os.flush();
		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
		}
		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		StringBuilder responseStringBuilder = new StringBuilder();
		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			responseStringBuilder.append(output);
		}
		conn.disconnect();
		return responseStringBuilder.toString();
	}
}
