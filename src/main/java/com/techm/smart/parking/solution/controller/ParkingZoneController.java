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

import com.techm.smart.parking.solution.database.repo.ParkingZoneRepo;
import com.techm.smart.parking.solution.domain.ParkingZone;
import com.techm.smart.parking.solution.exception.ResourceNotFound;
import com.techm.smart.parking.solution.exception.ServiceException;

@RestController
@RequestMapping("/pkzone")
public class ParkingZoneController {
	
	/** Logger instance to log the incidents. */
	Logger logger = LoggerFactory.getLogger(ParkingZoneController.class);

	@Autowired
	private ParkingZoneRepo parkingZoneRepo;
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody 
	public ParkingZone saveParkingZone(@RequestBody ParkingZone pkZone){
		logger.info("saving a parking zone object. Object: " + pkZone);
		ParkingZone parkingZone = null;
		try {
			parkingZone =parkingZoneRepo.save(pkZone);
		} catch (Exception exp) {
			exp.printStackTrace();
			String errorMsg = "Could not save parking slot due to internal issue. Error: " + exp.getCause();
			logger.error(errorMsg);
			throw new ServiceException(exp.getMessage());
		} 
		
		return parkingZone;
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	@ResponseBody
	public ParkingZone updateParkingZone(@RequestBody ParkingZone pkZone){
		ParkingZone pkZoneObj = null;
		ParkingZone parkingZone = null;
		
		logger.info("Retrieving of parking slot object from database. pkZoneId: " + pkZone.getPkZoneId());
		try {
			pkZoneObj = parkingZoneRepo.fetchByPkZoneId(pkZone.getPkZoneId());
			if(pkZoneObj != null){
				logger.info("Deleting of existing parking zone object from database. pkZoneId: " + pkZone.getPkZoneId());
				parkingZoneRepo.delete(pkZone);
			}
			logger.info("Updating/Saving of parking zone object into the database. pkZoneId: " + pkZone.getPkZoneId());
			parkingZone =parkingZoneRepo.save(pkZone);
		} catch (Exception exp) {
			exp.printStackTrace();
			String errorMsg = "Could not save parking zone due to internal issue. Error: " + exp.getCause();
			logger.error(errorMsg);
			throw new ServiceException(exp.getMessage());
			
		}
		
		return parkingZone;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public List<ParkingZone> getParkingZones(){
		List<ParkingZone> listPKZone = new ArrayList<>();
		logger.info("Retrieving of all parking zones objects from database.");
		try {
			parkingZoneRepo.findAll().forEach(e -> listPKZone.add(e));
		}  catch (Exception exp) {
			exp.printStackTrace();
			String errorMsg = "Could not save parking zone due to internal issue. Error: " + exp.getCause();
			logger.error(errorMsg);
			throw new ServiceException(exp.getMessage());
			
		}
		
		return listPKZone;
	}
	
	@RequestMapping(value = "/{pkZoneId}", method=RequestMethod.GET)
	@ResponseBody
	public ParkingZone getParkingZoneByParkingZone(@PathVariable String pkZoneId){
		logger.info("Retrieving of parking zone object from database. pkZoneId: " + pkZoneId);
		ParkingZone pkZone = null;
		try {
			pkZone = parkingZoneRepo.fetchByPkZoneId(pkZoneId);
			if(pkZone == null) {
				throw new ResourceNotFound("A resource with following ID was not found: "+pkZoneId);
			}
		} catch (Exception exp) {
			throw new ServiceException(exp.getMessage());
		}
		
		
		return pkZone;
	}
	
	@RequestMapping(value = "/{pkZoneId}", method=RequestMethod.DELETE)
	@ResponseBody
	public void deleteParkingZoneByParkingZone(@PathVariable String pkZoneId){
		logger.info("Retrieving of parking zone  object from database. pkZoneId: " + pkZoneId);
		ParkingZone pkZone = null;
		try {
			pkZone = parkingZoneRepo.fetchByPkZoneId(pkZoneId);
			if(pkZone != null){
				logger.info("Deleting of parking zone object. Object: " + pkZone);
				parkingZoneRepo.delete(pkZone);
			}
			else {
				throw new ResourceNotFound("A resource with following ID was not found: "+pkZoneId);
			}
		} catch (Exception exp) {
			throw new ServiceException(exp.getMessage());
		}
		
		
	}
}
