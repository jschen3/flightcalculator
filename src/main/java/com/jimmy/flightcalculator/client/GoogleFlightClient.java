package com.jimmy.flightcalculator.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jimmy.flightcalculator.googleflight.objects.Request;
import com.jimmy.flightcalculator.googleflight.objects.Response;

public class GoogleFlightClient {
	static String GOOGLE_FLIGHT_URL = "https://www.googleapis.com/qpxExpress/v1/trips/search?key=";
	public static String makeRestCall(Request request, String apiKey) throws IOException{
		String urlToCall = GOOGLE_FLIGHT_URL+apiKey;
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
			throw new RuntimeException("Failed : HTTP error code : "
				+ conn.getResponseCode());
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
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
