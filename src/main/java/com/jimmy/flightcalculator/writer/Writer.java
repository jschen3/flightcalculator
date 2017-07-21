package com.jimmy.flightcalculator.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jimmy.flightcalculator.objects.Average;
import com.jimmy.flightcalculator.objects.Flight;

public class Writer {
	private final String CHEAPEST_FLIGHTS_FILE = "cheapest_flights.csv";
	private final String FLIGHT_INFO_FILE = "flight_info.csv";
	private final String CHEAPEST_FLIGHT_INFO_FOLDER = "cheapest_flight_info";
	private final String FLIGHT_INFO_FOLDER = "flight_info";
	private final String[] CHEAPEST_FLIGHT_COLUMNS = {"request_time", "flight_name", "price", "carrier", "flight_number", "first_depature_time"};
	private String flightRequestFolderPath;
	private File cheapestFolder;
	private	File flightInfoFolder;
	private File cheapFile;
	private File flightFile;
	public void write(String origin ,String destination, String flightDate, String baseFilePath, List<Flight> flights) throws IOException{
		setupFiles(origin, destination, flightDate, baseFilePath);
		List<Flight> cheapList = new ArrayList<Flight>(flights);
		cheapList=cheapList.subList(0, 5);
		writeCheapFiles(cheapList);
		writeFlightInfoFiles(flights);
		Average average = new Average();
		average.calculateNewAverage(cheapList, flightRequestFolderPath);
		average.writeJson(flightRequestFolderPath);
	}
	private void writeFlightInfoFiles(List<Flight> flights) throws IOException {
		for(Flight flight:flights){
			writeRow(flightFile, flight.toColumns());
		}
		for(Flight flight:flights){
			flight.toFile(flightInfoFolder.getAbsolutePath());
		}
		
	}
	private void writeCheapFiles(List<Flight> cheapList) throws IOException {
		for(Flight flight:cheapList){
			writeRow(cheapFile, flight.toColumns());
		}
		for(Flight flight:cheapList){
			flight.toFile(cheapestFolder.getAbsolutePath());
		}
		
	}
	private void setupFiles(String origin, String destination, String flightDate, String baseFilePath) throws IOException{
		File baseFolder=createFolder(baseFilePath);
		this.flightRequestFolderPath = baseFolder.getAbsolutePath()+"/"+origin +"_to_"+destination + "_"+flightDate;
		File flightRequestFolder=createFolder(flightRequestFolderPath);
		this.cheapFile=writeFlightInfoFile(flightRequestFolder.getAbsolutePath(), CHEAPEST_FLIGHTS_FILE);
		this.flightFile=writeFlightInfoFile(flightRequestFolder.getAbsolutePath(), FLIGHT_INFO_FILE);
		this.cheapestFolder=createFolder(flightRequestFolder.getAbsolutePath()+"/"+CHEAPEST_FLIGHT_INFO_FOLDER);
		this.flightInfoFolder=createFolder(flightRequestFolder.getAbsolutePath()+"/"+FLIGHT_INFO_FOLDER);
		createAverageFile(flightRequestFolderPath);
	}
	private File createFolder(String folderPath){
		File folder = new File(folderPath);
		if (!folder.exists()){
			folder.mkdirs();
		}
		return folder;
	
	}
	private File writeFlightInfoFile(String baseFlightPath, String fileName) throws IOException{
		File newCheapestFile = new File(baseFlightPath+"/"+fileName);
		if(!newCheapestFile.exists()){
			newCheapestFile.createNewFile();
			writeRow(newCheapestFile, CHEAPEST_FLIGHT_COLUMNS);
		}
		return newCheapestFile;
		
	}
	public void writeRow(File f, String[] columns) throws IOException{
		FileWriter fw = new FileWriter(f.getAbsolutePath(), true);
		if (columns.length>0){
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(columns[0]);
			for(int i=1;i<columns.length;i++){
				bw.write(","+columns[i]);
			}
			bw.newLine();;
			bw.flush();
		}
		fw.close();
	}
	public void writeFlights(String baseFlightPath, List<Flight> flights) throws JsonGenerationException, JsonMappingException, IOException{
		for(Flight flight:flights){
			String fileName = baseFlightPath + "/"+ flight.getFlightName();
			flight.toFile(fileName);
		}
	}
	private void createAverageFile(String baseFilePath) throws JsonGenerationException, JsonMappingException, IOException{
		Average average = new Average();
		average.writeJson(baseFilePath);
	}
}
