package com.techm.smart.parking.solution.service;

import java.util.TimeZone;

import com.techm.smart.parking.solution.utils.ParkInput;

public interface ParkingService {
	
	String park(ParkInput parkInput);
	
	String unpark(ParkInput parkInput);
	
	String extendPark(ParkInput parkInput);
	
	void setControllerTimeZone(String ctrlId, TimeZone timeZone);
}
