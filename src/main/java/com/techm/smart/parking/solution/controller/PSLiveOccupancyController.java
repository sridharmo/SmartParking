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

import com.techm.smart.parking.solution.database.repo.PSLiveOccupancyRepo;
import com.techm.smart.parking.solution.domain.PSLiveOccupancy;
import com.techm.smart.parking.solution.exception.ResourceNotFound;
import com.techm.smart.parking.solution.exception.ServiceException;

@RestController
@RequestMapping("/occupy")
public class PSLiveOccupancyController {
	
	/** Logger instance to log the incidents. */
	Logger logger = LoggerFactory.getLogger(PSLiveOccupancyController.class); 

	@Autowired
	private PSLiveOccupancyRepo occupancyRepo;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public PSLiveOccupancy savePSLiveOccupancy(@RequestBody PSLiveOccupancy pslOccupancy) {
		logger.info("saving a parking slot live occupancy object. Object: " + pslOccupancy);
		PSLiveOccupancy psLiveOccupancy = null;
		try {
			psLiveOccupancy = occupancyRepo.save(pslOccupancy);
		} catch (Exception exp) {
			exp.printStackTrace();
			String errorMsg = "Could not save parking slot live occupancy due to internal issue. Error: " + exp.getCause();
			logger.error(errorMsg);
			throw new ServiceException(exp.getMessage());
		} 
		 
		return psLiveOccupancy;
	}

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public PSLiveOccupancy updatePSLiveOccupancy(@RequestBody PSLiveOccupancy pslOccupancy) {
		logger.info("Retrieving of parking slot live occupancy object from database. pkSlotId: " + pslOccupancy.getPkSlotId());
		PSLiveOccupancy psLiveOccupancy = null;
		try {
			psLiveOccupancy = occupancyRepo.fetchById(pslOccupancy.getPkSlotId());
			if (psLiveOccupancy != null) {
				logger.info("Deleting of existing parking slot live occupancy object from database. pkSlotId: " + pslOccupancy.getPkSlotId());
				occupancyRepo.delete(psLiveOccupancy);
			}
			logger.info("Updating/Saving of parking slot live occupancy object into the database. pkSlotId: " + pslOccupancy.getPkSlotId());
			psLiveOccupancy = occupancyRepo.save(pslOccupancy);
		} catch (Exception exp) {
			exp.printStackTrace();
			String errorMsg = "Could not save parking slot live occupancy due to internal issue. Error: " + exp.getCause();
			logger.error(errorMsg);
			throw new ServiceException(exp.getMessage());
		}
		
		return psLiveOccupancy;
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<PSLiveOccupancy> getPSLiveOccupancies() {
		List<PSLiveOccupancy> listPSLiveOccupancies = new ArrayList<>();
		logger.info("Retrieving of all parking slots live occupancy objects from database.");
		try {
			occupancyRepo.findAll().forEach(e -> listPSLiveOccupancies.add(e));
		} catch (Exception exp) {
			exp.printStackTrace();
			String errorMsg = "Could not save parking zone due to internal issue. Error: " + exp.getCause();
			logger.error(errorMsg);
			throw new ServiceException(exp.getMessage());
			
		}
		
		return listPSLiveOccupancies;

	}

	@RequestMapping(value = "/{pkSlotId}", method = RequestMethod.GET)
	@ResponseBody
	public PSLiveOccupancy getPSLiveOccupancyBySlotId(@PathVariable String pkSlotId) {
		logger.info("Retrieving of parking slot live occupancy object from database. pkSlotId: " + pkSlotId);
		PSLiveOccupancy psLiveOccupancy = null;
		try {
			psLiveOccupancy = occupancyRepo.fetchById(pkSlotId);
			if(psLiveOccupancy == null) {
				throw new ResourceNotFound("A resource with following ID was not found: "+pkSlotId);
			}
		} catch (Exception exp) {
			throw new ServiceException(exp.getMessage());
		}
		
		return psLiveOccupancy;

	}

	@RequestMapping(value = "/{pkSlotId}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deletePSLiveOccupancy(@PathVariable String pkSlotId) {
		logger.info("Retrieving of parking slot live occupancy object from database. pkSlotId: " + pkSlotId);
		PSLiveOccupancy psLiveOccupancy = null;
		try {
			psLiveOccupancy = occupancyRepo.fetchById(pkSlotId);
			if (psLiveOccupancy != null) {
				logger.info("Deleting of parking slot live occupency object. Object: " + psLiveOccupancy);
				occupancyRepo.delete(psLiveOccupancy);
			}	else {
				throw new ResourceNotFound("A resource with following ID was not found: "+pkSlotId);
			}
		} catch (Exception exp) {
			throw new ServiceException(exp.getMessage());
		}

	}
}
