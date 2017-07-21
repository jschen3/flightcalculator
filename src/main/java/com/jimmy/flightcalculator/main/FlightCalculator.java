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

import com.jimmy.flightcalculator.client.GoogleFlightClient;
import com.jimmy.flightcalculator.googleflight.objects.Request;
import com.jimmy.flightcalculator.googleflight.objects.Response;
import com.jimmy.flightcalculator.objects.Flight;
import com.jimmy.flightcalculator.writer.Writer;

public class FlightCalculator {
	private final static String CONFIG_FILE = "config.properties";
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
	public static void main(String args[]) throws Exception{
		if (args.length!=3){
			throw new Exception("Invalid arguments: Usage [0] origin, [1] destination, [2] flightDate");
		}
		else{
			origin = args[0];
			destination = args[1];
			date = args[2];
			initProperties();
			List<Flight> flights = callServer(origin, destination, date);
			processResults(origin, destination, date, flights);
		}
	}
	public static void initProperties() throws IOException{
		 InputStream configStream = FlightCalculator.class.getClass().getResourceAsStream(CONFIG_FILE);
		 if (configStream == null){
			 configStream = new FileInputStream(new File("src/main/resources/config.properties"));
		 }
		 Properties properties = new Properties();
		 properties.load(configStream);
		 apiKey = properties.getProperty("apiKey");
		 emailFrom = properties.getProperty("emailFrom");
		 emailPassword = properties.getProperty("emailPassword");
		 emailTo = properties.getProperty("emailTo");
		 baseFilePath=properties.getProperty("baseFilePath");
		 flightsForAverage=Integer.parseInt(properties.getProperty("flightsForAverage"));
		 differenceBetweenAverage=Integer.parseInt(properties.getProperty("differenceBetweenAverage"));
		 flights = properties.getProperty("flights");
		 requestDate = new Date();
	}
	public static List<Flight> callServer(String origin, String destination, String date) throws ParseException, IOException{
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		Date dateDate=sdf.parse(date);
		Request request = new Request(origin, destination, dateDate, 1, flights);
		GoogleFlightClient gfg = new GoogleFlightClient();
		String response=gfg.makeRestCall(request, apiKey);
		return Response.parseResponse(response, origin, destination, createDateString(requestDate));
	}
	public static void processResults(String origin, String destination, String date, List<Flight> flights) throws IOException{
		Writer writer = new Writer();
		Collections.sort(flights);
		writer.write(origin, destination, date, baseFilePath, flights);	
	}
	public static String createDateString(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyyy-HH");
		return sdf.format(date);
	}
}
