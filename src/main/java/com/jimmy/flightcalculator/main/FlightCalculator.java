package com.jimmy.flightcalculator.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.jimmy.flightcalculator.client.GoogleFlightClient;
import com.jimmy.flightcalculator.client.GoogleFlightParser;
import com.jimmy.flightcalculator.client.GoogleFlightRequest;
import com.jimmy.flightcalculator.objects.Flight;
import com.jimmy.flightcalculator.processor.FlightProcessor;

public class FlightCalculator {
	private final static String CONFIG_FILE = "/config.properties";
	private static String apiKey;
	private static String emailFrom;
	private static String emailPassword;
	private static String emailTo;
	private static String baseFilePath;
	private static int flightsForAverage;
	private static int differenceBetweenAverage;
	private static String flights;
	private static String origin;
	private static String destination;
	private static String date;
	private static Date requestDate;

	public static void main(String args[]) throws Exception {
		if (args.length != 3) {
			throw new Exception("Invalid arguments: Usage [0] origin, [1] destination, [2] flightDate");
		} else {
			origin = args[0];
			destination = args[1];
			date = args[2];
			initProperties();
			List<Flight> flights = callServer(origin, destination, date);
			processResults(origin, destination, date, flights);
		}
	}

	private static void initProperties() throws IOException {
		InputStream configStream = FlightCalculator.class.getClass().getResourceAsStream(CONFIG_FILE);
		if (configStream == null) {
			configStream = new FileInputStream(new File("src/main/resources/config.properties"));
		}
		Properties properties = new Properties();
		properties.load(configStream);
		apiKey = properties.getProperty("apiKey");
		emailFrom = properties.getProperty("emailFrom");
		emailPassword = properties.getProperty("emailPassword");
		emailTo = properties.getProperty("emailTo");
		baseFilePath = properties.getProperty("baseFilePath");
		flightsForAverage = Integer.parseInt(properties.getProperty("flightsForAverage"));
		differenceBetweenAverage = Integer.parseInt(properties.getProperty("differenceBetweenAverage"));
		flights = properties.getProperty("flights");
		requestDate = new Date();
	}

	private static List<Flight> callServer(String origin, String destination, String date)
			throws ParseException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		Date dateDate = sdf.parse(date);
		GoogleFlightRequest request = new GoogleFlightRequest(origin, destination, dateDate, 1, flights);
		GoogleFlightClient gfg = new GoogleFlightClient();
		String response = gfg.callGoogleFlights(request, apiKey);
		return GoogleFlightParser.parseResponse(response, origin, destination, createDateString(requestDate));
	}

	private static void processResults(String origin, String destination, String date, List<Flight> flights)
			throws IOException, AddressException, MessagingException {
		FlightProcessor flightProcessor = new FlightProcessor(emailTo, emailFrom, emailPassword,
				differenceBetweenAverage, flightsForAverage);
		Collections.sort(flights);
		flightProcessor.process(origin, destination, date, baseFilePath, flights);
	}

	public static String createDateString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy-HH");
		return sdf.format(date);
	}
}
