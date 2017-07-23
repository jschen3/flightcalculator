package com.jimmy.flightcalculator.objects;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Average {
	private float averagePrice;
	private int totalCount;
	String AVERAGE_PATH = "average.json";

	public float getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(float averagePrice) {
		this.averagePrice = averagePrice;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public void getAverage(String baseFilePath) throws JsonParseException, JsonMappingException, IOException {
		String path = baseFilePath + "/" + AVERAGE_PATH;
		ObjectMapper mapper = new ObjectMapper();
		Average average = mapper.readValue(new File(path), Average.class);
		this.averagePrice = average.getAveragePrice();
		this.totalCount = average.getTotalCount();

	}

	public void calculateNewAverage(List<Flight> cheapestFlights, String baseFilePath)
			throws JsonParseException, JsonMappingException, IOException {
		if (this.totalCount == 0) {
			getAverage(baseFilePath);
		}
		float totalPrice = 0;
		for (Flight flight : cheapestFlights) {
			totalPrice += flight.getPrice();
		}
		float newTotalPrice = averagePrice * (float) totalCount + totalPrice;
		totalCount += cheapestFlights.size();
		this.averagePrice = newTotalPrice / (float) totalCount;
	}

	public void writeJson(String baseFilePath) throws JsonGenerationException, JsonMappingException, IOException {
		String path = baseFilePath + "/" + AVERAGE_PATH;
		File f = new File(path);
		if (!f.exists()) {
			f.createNewFile();
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.writerWithDefaultPrettyPrinter().writeValue(new File(path), this);
		;
	}

}
