package com.jimmy.flightcalculator.client;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
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

@SuppressWarnings("unchecked")
public class GoogleFlightParser {
	public static List<Flight> parseResponse(File f, String origin, String destination, String date)
			throws IOException {
		String response = new String(Files.readAllBytes(f.toPath()));
		return parseResponse(response, origin, destination, date);
	}

	public static List<Flight> parseResponse(String response, String origin, String destination, String date)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> responseMap = mapper.readValue(response, Map.class);
		List<Map<String, Object>> tripOption = (List<Map<String, Object>>) ((Map<String, Object>) responseMap
				.get("trips")).get("tripOption");
		return parseTripOptions(tripOption, origin, destination, date);
	}

	private static List<Flight> parseTripOptions(List<Map<String, Object>> map, String origin, String destination,
			String date) {
		List<Flight> flights = new ArrayList<Flight>();
		int count = 0;
		for (Map<String, Object> tripOption : map) {
			Flight flight = new Flight();
			flight.setRequestTime(FlightCalculator.createDateString(new Date()));
			String tripOptionPrice = (String) tripOption.get("saleTotal");
			flight.setPrice(parseTripOptionPrice(tripOptionPrice));
			List<FlightSegment> segments = parseSlices((List<Map<String, Object>>) tripOption.get("slice"));
			if (segments != null) {
				flight.setFlightName(origin + "_to_" + destination + "_" + date + "_flight_" + count);
				flight.setFlightSegments(segments);
				flights.add(flight);
				count++;
			} else {
				continue;
			}
		}
		return flights;
	}

	private static List<FlightSegment> parseSlices(List<Map<String, Object>> slices) {
		List<FlightSegment> flightSegments = new ArrayList<FlightSegment>();
		for (Map<String, Object> slice : slices) {
			List<Map<String, Object>> segments = (List<Map<String, Object>>) slice.get("segment");
			for (Map<String, Object> segment : segments) {
				int duration = (Integer) segment.get("duration");
				String carrier = (String) ((Map<String, Object>) segment.get("flight")).get("carrier");
				String flightNumber = (String) ((Map<String, Object>) segment.get("flight")).get("number");
				String bookingCode = (String) segment.get("bookingCode");
				List<Map<String, Object>> legs = (List<Map<String, Object>>) segment.get("leg");
				Map<String, String> leg = parseLegs(legs);
				if (leg == null) {
					return null;
				} else {
					String aircraft = leg.get("aircraft");
					String arrivalTime = leg.get("arrivalTime");
					String departureTime = leg.get("departureTime");
					String origin = leg.get("origin");
					String destination = leg.get("destination");
					FlightSegment flightSegment = new FlightSegment(carrier, duration, flightNumber, bookingCode,
							departureTime, arrivalTime, origin, destination, aircraft);
					flightSegments.add(flightSegment);
				}
			}
		}
		return flightSegments;
	}

	private static Map<String, String> parseLegs(List<Map<String, Object>> legs) {
		// TODO: deal with multiple legs
		Map<String, String> leg = new HashMap<String, String>();
		if (legs.size() > 1) {
			return null;
		} else {
			leg.put("aircraft", (String) legs.get(0).get("aircraft"));
			leg.put("arrivalTime", (String) legs.get(0).get("arrivalTime"));
			leg.put("departureTime", (String) legs.get(0).get("departureTime"));
			leg.put("origin", (String) legs.get(0).get("origin"));
			leg.put("destination", (String) legs.get(0).get("destination"));
		}
		return leg;
	}

	private static float parseTripOptionPrice(String tripOptionPrice) {
		int start = 0;
		int zero = (int) '0';
		int nine = (int) '9';
		int tripCharInt = (int) tripOptionPrice.charAt(start);
		while (tripCharInt < zero || tripCharInt > nine) {
			start++;
			tripCharInt = (int) tripOptionPrice.charAt(start);

		}
		return Float.parseFloat(tripOptionPrice.substring(start));
	}

}
