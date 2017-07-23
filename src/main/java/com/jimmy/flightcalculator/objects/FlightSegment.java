package com.jimmy.flightcalculator.objects;

public class FlightSegment {
	@Override
	public String toString() {
		return "FlightSegment [carrier=" + carrier + ", duration=" + duration + ", flightNumber=" + flightNumber
				+ ", bookingCode=" + bookingCode + ", depatureTime=" + depatureTime + ", arrivalTime=" + arrivalTime
				+ ", origin=" + origin + ", destination=" + destination + ", aircraft=" + aircraft + "]";
	}

	String carrier;
	int duration;
	String flightNumber;
	String bookingCode;
	String depatureTime;
	String arrivalTime;
	String origin;
	String destination;
	String aircraft;

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getBookingCode() {
		return bookingCode;
	}

	public void setBookingCode(String bookingCode) {
		this.bookingCode = bookingCode;
	}

	public String getDepatureTime() {
		return depatureTime;
	}

	public void setDepatureTime(String depatureTime) {
		this.depatureTime = depatureTime;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public FlightSegment(String carrier, int duration, String flightNumber, String bookingCode, String depatureTime,
			String arrivalTime, String origin, String destination, String aircraft) {
		this.carrier = carrier;
		this.duration = duration;
		this.flightNumber = flightNumber;
		this.bookingCode = bookingCode;
		this.depatureTime = depatureTime;
		this.arrivalTime = arrivalTime;
		this.origin = origin;
		this.destination = destination;
		this.aircraft = aircraft;
	}
}
