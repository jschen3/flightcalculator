package com.jimmy.flightcalculator.googleflight.objects;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Request {
	private List<HashMap<String, String>> slice;
	private Map<String, String> passengers;
	private String solutions;
	public List<HashMap<String, String>> getSlice() {
		return slice;
	}
	public void setSlice(List<HashMap<String, String>> slice) {
		this.slice = slice;
	}
	public Map<String, String> getPassengers() {
		return passengers;
	}
	public void setPassengers(Map<String, String> passengers) {
		this.passengers = passengers;
	}
	public String getSolutions() {
		return solutions;
	}
	public void setSolutions(String solutions) {
		this.solutions = solutions;
	}
	public boolean isRefundable() {
		return refundable;
	}
	public void setRefundable(boolean refundable) {
		this.refundable = refundable;
	}
	private boolean refundable = false;
	public void createSlice(String origin, String destination, Date date){
		if (slice==null){
			slice = new ArrayList<HashMap<String, String>>();
		}
		HashMap<String, String> sliceMap = new HashMap<String, String>();
		sliceMap.put("origin", origin);
		sliceMap.put("destination", destination);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = sdf.format(date);
		sliceMap.put("date", dateString);
		slice.add(sliceMap);
	}
	public void createPassengers(int adultCount, int childCount){
		if (passengers==null){
			passengers = new HashMap<String, String>();
		}
		passengers.put("adultCount", adultCount+"");
		passengers.put("childCount", childCount+"");
	}
	public void createPassengers(int adultCount){
		if (passengers==null){
			passengers = new HashMap<String, String>();
		}
		passengers.put("adultCount", adultCount+"");
	}
	public Request(){}
	public Request(String origin, String destination, Date date, int adultCount, String flights){
		createSlice(origin, destination, date);
		createPassengers(adultCount);
		this.solutions=flights;
	}
	public String createRequestJson() throws JsonProcessingException{
		ObjectMapper mapper  = new ObjectMapper();
		Map<String, Request> request = Collections.singletonMap("request", this);
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
	}
	
	
}
