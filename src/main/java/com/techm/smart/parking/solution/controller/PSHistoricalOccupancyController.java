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

import com.techm.smart.parking.solution.database.repo.PSHistoricalOccupancyRepo;
import com.techm.smart.parking.solution.domain.PSHistoricalOccupancy;
import com.techm.smart.parking.solution.exception.ResourceNotFound;
import com.techm.smart.parking.solution.exception.ServiceException;

@RestController
@RequestMapping("/occupy/historical")
public class PSHistoricalOccupancyController {
	
	/** Logger instance to log the incidents. */
	Logger logger = LoggerFactory.getLogger(PSHistoricalOccupancyController.class); 

	@Autowired
	private PSHistoricalOccupancyRepo historicalOccupancyRepo;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public PSHistoricalOccupancy savePSHistoricalOccupancy(@RequestBody PSHistoricalOccupancy pslOccupancy) {
		logger.info("saving a parking slot historical occupency object. Object: " + pslOccupancy);
		PSHistoricalOccupancy psHistoricalOccupancy = null;
		try {
			psHistoricalOccupancy = historicalOccupancyRepo.save(pslOccupancy);
		}
		catch (Exception exp) {
			exp.printStackTrace();
			String errorMsg = "Could not save parking zone historical occupency due to internal issue. Error: " + exp.getCause();
			logger.error(errorMsg);
			throw new ServiceException(exp.getMessage());
		} 
		return psHistoricalOccupancy;
	}

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public PSHistoricalOccupancy updatePSHistoricalOccupancy(@RequestBody PSHistoricalOccupancy pslOccupancy) {
		logger.info("Retrieving of parking zone historical occupency object from database. pkSlotId: " + pslOccupancy.getPkSlotId());
		PSHistoricalOccupancy psHistoricalOccupancy = null;
		try {
			psHistoricalOccupancy = historicalOccupancyRepo.fetchById(pslOccupancy.getPkSlotId());
			if (psHistoricalOccupancy != null) {
				logger.info("Deleting of existing parking slot historical occupency object from database. pkSlotId: " + pslOccupancy.getPkSlotId());
				historicalOccupancyRepo.delete(psHistoricalOccupancy);
			}
			logger.info("Updating/Saving of parking slot historical occupency object into the database. pkSlotId: " + pslOccupancy.getPkSlotId());
			psHistoricalOccupancy = historicalOccupancyRepo.save(pslOccupancy);
		} catch (Exception exp) {
			exp.printStackTrace();
			String errorMsg = "Could not save parking slot historical occupency due to internal issue. Error: " + exp.getCause();
			logger.error(errorMsg);
			throw new ServiceException(exp.getMessage());
			
		}
		
		return psHistoricalOccupancy;
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<PSHistoricalOccupancy> getPSHitoricalOccupancies() {
		List<PSHistoricalOccupancy> listPSHistoricalOccupancies = new ArrayList<>();
		logger.info("Retrieving of all parking slots historical occupency objects from database.");
		try {
			historicalOccupancyRepo.findAll().forEach(e -> listPSHistoricalOccupancies.add(e));
		}
		 catch (Exception exp) {
				exp.printStackTrace();
				String errorMsg = "Could not save parking zone due to internal issue. Error: " + exp.getCause();
				logger.error(errorMsg);
				throw new ServiceException(exp.getMessage());
				
			}
		return listPSHistoricalOccupancies;

	}

	@RequestMapping(value = "/{pkSlotId}", method = RequestMethod.GET)
	@ResponseBody
	public PSHistoricalOccupancy getPSHitoricalOccupancyBySlotId(@PathVariable String pkSlotId) {
		logger.info("Retrieving of parking slot historical occupency object from database. pkSlotId: " + pkSlotId);
		PSHistoricalOccupancy psHistoricalOccupancy = null;
		try {
			psHistoricalOccupancy = historicalOccupancyRepo.fetchById(pkSlotId);
			if(psHistoricalOccupancy == null) {
				throw new ResourceNotFound("A resource with following ID was not found: "+pkSlotId);
			}
		} catch (Exception exp) {
			throw new ServiceException(exp.getMessage());
		}
		return psHistoricalOccupancy;

	}

	@RequestMapping(value = "/{pkSlotId}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deletePSHistoricalOccupancy(@PathVariable String pkSlotId) {
		logger.info("Retrieving of parking slot historical occupency object from database. pkSlotId: " + pkSlotId);
		PSHistoricalOccupancy psHistoricalOccupancy = null;
		
		try {
			psHistoricalOccupancy = historicalOccupancyRepo.fetchById(pkSlotId);
			if (psHistoricalOccupancy != null) {
				logger.info("Deleting of parking slot historical occupency object. Object: " + psHistoricalOccupancy);
				historicalOccupancyRepo.delete(psHistoricalOccupancy);
			}
			else {
				throw new ResourceNotFound("A resource with following ID was not found: "+pkSlotId);
			}
		} catch (Exception exp) {
			throw new ServiceException(exp.getMessage());
		}
		
	}
}
