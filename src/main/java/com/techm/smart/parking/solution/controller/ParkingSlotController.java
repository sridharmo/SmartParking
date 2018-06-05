package com.techm.smart.parking.solution.controller;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.techm.smart.parking.solution.database.repo.ParkingSlotRepo;
import com.techm.smart.parking.solution.domain.ParkingSlot;
import com.techm.smart.parking.solution.exception.CassandraHostNotAvailableException;
import com.techm.smart.parking.solution.exception.ResourceNotFound;
import com.techm.smart.parking.solution.exception.ServiceException;

@RestController
@RequestMapping("/pkslot")
public class ParkingSlotController {
	
	/** Logger instance to log the incidents. */ 
	Logger logger = LoggerFactory.getLogger(ParkingSlotController.class);

	@Autowired
	private ParkingSlotRepo parkingSlotRepo;
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public ParkingSlot saveParkingSlot(@RequestBody ParkingSlot pkSlot)  {
		ParkingSlot parkingSlot = null;
		
			logger.info("saving a parking slot object. Object: " + pkSlot);
		try {
			parkingSlot = parkingSlotRepo.save(pkSlot);
		} catch (Exception exp) {
			exp.printStackTrace();
			String errorMsg = "Could not save parking slot due to internal issue. Error: " + exp.getCause();
			logger.error(errorMsg);
			throw new ServiceException(exp.getMessage());
		} 
		
		return parkingSlot;
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	@ResponseBody
	public ParkingSlot updateParkingSlot(@RequestBody ParkingSlot pkSlot) {
		ParkingSlot pksObj = null;
		ParkingSlot parkingSlot = null;
		logger.info("Retrieving of parking slot  object from database. deviceId: " + pkSlot.getDeviceId());
		try {
			pksObj =  parkingSlotRepo.fetchByDeviceId(pkSlot.getDeviceId());
			if(pksObj != null){
				logger.info("Deleting of existing parking slot object from database. deviceId: " + pkSlot.getDeviceId());
				parkingSlotRepo.delete(pksObj);
			}
			logger.info("Updating/Saving of parking slot object into the database. deviceId: " + pkSlot.getDeviceId());
			parkingSlot = parkingSlotRepo.save(pkSlot);
		} catch (Exception exp) {
			exp.printStackTrace();
			String errorMsg = "Could not save parking slot due to internal issue. Error: " + exp.getCause();
			logger.error(errorMsg);
			throw new ServiceException(exp.getMessage());
			
		}
		return parkingSlot;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public List<ParkingSlot> getParkingSlots() throws CassandraHostNotAvailableException{
		List<ParkingSlot> listPKSlot = new ArrayList<>();
		logger.info("Retrieving of all parking slots objects from database.");
		try {
			parkingSlotRepo.findAll().forEach(e -> listPKSlot.add(e));
		} catch (Exception exp) {
			exp.printStackTrace();
			String errorMsg = "Could not save parking slot due to internal issue. Error: " + exp.getCause();
			logger.error(errorMsg);
			throw new ServiceException(exp.getMessage());
			
		}
		
		return listPKSlot;
	}
	
	@RequestMapping(value = "/{deviceId}", method=RequestMethod.GET)
	@ResponseBody
	public ParkingSlot getParkingSlotByDeviceId(@PathVariable String deviceId) throws ResourceNotFound {
		ParkingSlot parkingSlot= null;
		logger.info("Retrieving of parking slot  object from database. deviceId: " + deviceId);
		try {
			//UUID newDeviceId = UUID.fromString(deviceId);
			parkingSlot = parkingSlotRepo.fetchByDeviceId(deviceId);
			if(parkingSlot == null) {
				throw new ResourceNotFound("A resource with following ID was not found: "+deviceId);
			}
		} catch (Exception exp) {
			throw new ServiceException(exp.getMessage());
		}
		
		return parkingSlot;
	}
	
	@RequestMapping(value = "/{deviceId}", method=RequestMethod.DELETE)
	@ResponseBody
	public void deleteParkingSlotByDeviceId(@PathVariable String deviceId) throws ResourceNotFound{
		logger.info("Retrieving of parking slot object from database. deviceId: " + deviceId);
		ParkingSlot parkingSlot = null;
		try {
			
			parkingSlot = parkingSlotRepo.fetchByDeviceId(deviceId);
			if(parkingSlot != null){
				logger.info("Deleting of parking slot object. Object: " + parkingSlot);
				parkingSlotRepo.delete(parkingSlot);
			} else {
				throw new ResourceNotFound("A resource with following ID was not found: "+deviceId);
			}
		}  catch (Exception exp) {
			throw new ServiceException(exp.getMessage());
		}
		
	}

}
