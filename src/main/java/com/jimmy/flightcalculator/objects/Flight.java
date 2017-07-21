package com.jimmy.flightcalculator.objects;
import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Flight implements Comparable<Flight>{
	String requestTime;
	String flightName;
	float price;
	List<FlightSegment> flightSegments;
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	public String getFlightName() {
		return flightName;
	}
	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public List<FlightSegment> getFlightSegments() {
		return flightSegments;
	}
	public void setFlightSegments(List<FlightSegment> flightSegments) {
		this.flightSegments = flightSegments;
	}
	@Override
	public String toString() {
		return "Flight [price=" + price + ", flightSegments=" + flightSegments + "]";
	}
	public String toJson() throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
	}
	public void toFile(String filePath) throws JsonGenerationException, JsonMappingException, IOException{
		File f = new File(filePath+"/"+getFlightName()+".json");
		if (!f.exists())
			f.createNewFile();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writerWithDefaultPrettyPrinter().writeValue(f, this);
	}
	public String[] toColumns(){
		String [] columns = new String[6];
		columns[0] = requestTime;
		columns[1] = flightName;
		columns[2] = price+"";
		if (flightSegments.size()>0){
			columns[3] = flightSegments.get(0).getCarrier();
			columns[4] = flightSegments.get(0).getFlightNumber();
			columns[5] = flightSegments.get(0).getDepatureTime();
		}
		return columns;
	}
	
	public int compareTo(Flight o) {
		//TODO: Consider flight segments in comparison. Fewer segments is better...
		return (int)(price-o.getPrice());
	} 
	
}
