package com.jimmy.flightcalculator.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
	private final String CHEAPEST_FLIGHTS_FILE = "cheapest_flights.csv";
	private final String FLIGHT_INFO_FILE = "flight_info.csv";
	private final String CHEAPEST_FLIGHT_INFO_FOLDER = "cheapest_flight_info";
	private final String FLIGHT_INFO_FOLDER = "flight_info";
	private final String[] CHEAPEST_FLIGHT_COLUMNS = {"request_time", "flight_name", "price", "carrier", "flight_number", "first_depature_time"};
	public void setupFiles(String origin, String destination, String flightDate, String baseFilePath, String requestDate) throws IOException{
		File baseFolder=createFolder(baseFilePath);
		String flightRequestFolderPath = origin +"_to_"+destination + "_"+flightDate;
		File flightRequestFolder=createFolder(baseFolder.getAbsolutePath()+"/"+flightRequestFolderPath);
		writeFlightInfoFile(flightRequestFolder.getAbsolutePath(), CHEAPEST_FLIGHTS_FILE);
		writeFlightInfoFile(flightRequestFolder.getAbsolutePath(), FLIGHT_INFO_FILE);
		createFolder(flightRequestFolder.getAbsolutePath()+"/"+CHEAPEST_FLIGHT_INFO_FOLDER);
		createFolder(flightRequestFolder.getAbsolutePath()+"/"+FLIGHT_INFO_FOLDER);
	}
	private File createFolder(String folderPath){
		File folder = new File(folderPath);
		if (!folder.exists()){
			folder.mkdirs();
		}
		return folder;
	
	}
	private void writeFlightInfoFile(String baseFlightPath, String fileName) throws IOException{
		File newCheapestFile = new File(baseFlightPath+"/"+fileName);
		if(!newCheapestFile.exists()){
			newCheapestFile.createNewFile();
			writeRow(newCheapestFile, CHEAPEST_FLIGHT_COLUMNS);
		}
		
	}
	public void writeRow(File f, String[] columns) throws IOException{
		FileWriter fw = new FileWriter(f.getAbsolutePath(), true);
		if (columns.length>0){
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(columns[0]);
			for(int i=1;i<columns.length;i++){
				bw.write(","+columns[1]);
			}
			bw.write("\n");
		}
	}
	
}
