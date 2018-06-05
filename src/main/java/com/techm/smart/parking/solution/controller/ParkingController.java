package com.techm.smart.parking.solution.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techm.smart.parking.solution.service.ParkingService;
import com.techm.smart.parking.solution.utils.ParkInput;

@RestController
@RequestMapping("/task/")
public class ParkingController {

	@Autowired
	private ParkingService parkingService;
	
	private SimpleDateFormat timeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	@RequestMapping(value = "car/park", method = RequestMethod.POST)
	public String parkCar(@RequestBody ParkInput parkData) {
		String msgStatus = parkingService.park(parkData);
		return msgStatus;
	}

	@RequestMapping(value = "car/unpark", method = RequestMethod.POST)
	public String unparkCar(@RequestBody ParkInput parkData) {
		String msgStatus = parkingService.unpark(parkData);
		return msgStatus;
	}

	@RequestMapping(value = "car/park/extend", method = RequestMethod.POST)
	public String extendParking(@RequestBody ParkInput parkData) {
		String msgStatus = parkingService.extendPark(parkData);
		return msgStatus;
	}

	@RequestMapping(value = "collector/{collectorId}/timezone/{timezone}", method = RequestMethod.POST)
	public String setCollectorTimezone(@PathVariable String collectorId, @PathVariable String timezone) {
		TimeZone timeZone = TimeZone.getTimeZone(timezone);
		parkingService.setControllerTimeZone(collectorId, timeZone);
		Calendar cal = Calendar.getInstance(timeZone); 
		return timeFormat.format(cal.getTime());
	}
}
