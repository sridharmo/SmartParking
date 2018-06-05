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

import com.techm.smart.parking.solution.database.repo.TagRepo;
import com.techm.smart.parking.solution.domain.Tag;
import com.techm.smart.parking.solution.exception.ResourceNotFound;
import com.techm.smart.parking.solution.exception.ServiceException;

@RestController
@RequestMapping("/tag")
public class TagController {
	
	/** Logger instance to log the incidents. */
	Logger logger = LoggerFactory.getLogger(TagController.class);

	@Autowired
	private TagRepo tagRepo;
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public Tag saveTag(@RequestBody Tag tag){
		logger.info("saving a tag object. Object: " + tag);
		Tag tagObj = null;
		try {
			tagObj = tagRepo.save(tag);
		} catch (Exception exp) {
			exp.printStackTrace();
			String errorMsg = "Could not save tag due to internal issue. Error: " + exp.getCause();
			logger.error(errorMsg);
			throw new ServiceException(exp.getMessage());
		} 
	
		return tagObj;
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	@ResponseBody
	public Tag updateTag(@RequestBody Tag tag){
		logger.info("Retrieving of tag  object from database. tagNumber: " + tag.getTagNumber());
		Tag tagObj = null;
		try {
			tagObj = tagRepo.fetchByTagNumber(tag.getTagNumber());
			if(tagObj != null){
				logger.info("Deleting of existing tag object from database.");
				tagRepo.delete(tag);
			}
			logger.info("Updating/Saving of tag object into the database");
			tagObj = tagRepo.save(tag);
		} catch (Exception exp) {
			exp.printStackTrace();
			String errorMsg = "Could not save tag due to internal issue. Error: " + exp.getCause();
			logger.error(errorMsg);
			throw new ServiceException(exp.getMessage());
		}
		
		return tagObj;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public List<Tag> getTags(){
		List<Tag> listTag = new ArrayList<>();
		logger.info("Retrieving of all tags objects from database.");
		try {
			tagRepo.findAll().forEach(e -> listTag.add(e));
		}  catch (Exception exp) {
			exp.printStackTrace();
			String errorMsg = "Could not save tag due to internal issue. Error: " + exp.getCause();
			logger.error(errorMsg);
			throw new ServiceException(exp.getMessage());
			
		}
		return listTag;
		
	}
	
	@RequestMapping(value = "/{tagNumber}", method=RequestMethod.GET)
	@ResponseBody
	public Tag getTagByTagNumber(@PathVariable String tagNumber) throws ResourceNotFound{
		logger.info("Retrieving of tag object from database. tagNumber: " + tagNumber);
		Tag tag = null;
		try {
			
			 tag = tagRepo.fetchByTagNumber(tagNumber);
			if(tag == null) {
				throw new ResourceNotFound("A resource with following ID was not found: "+tagNumber);
			}
		} catch (Exception exp) {
			throw new ServiceException(exp.getMessage());
		}
		
		return tag;
		
	}
	
	@RequestMapping(value = "/{tagNumber}", method=RequestMethod.DELETE)
	@ResponseBody
	public Tag deleteTagByTagNumber(@PathVariable String tagNumber){
		logger.info("Retrieving of tag object from database. tagNumber: " + tagNumber);
		Tag tag = null;
		try {
			tag = tagRepo.fetchByTagNumber(tagNumber);
			if(tag != null){
				logger.info("Deleting of tag object. Object: " + tag);
				tagRepo.delete(tag);
			} else {
				throw new ResourceNotFound("A resource with following ID was not found: "+tagNumber);
			}
		} catch (Exception exp) {
			throw new ServiceException(exp.getMessage());
		}
		
		return tag;
		
	}
}
