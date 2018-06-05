package com.techm.smart.parking.solution.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.techm.smart.parking.solution.database.repo.PersonRepo;
import com.techm.smart.parking.solution.domain.Person;

@RestController
@RequestMapping("/person")
public class PeronsController {
	

	
	@Autowired
	private PersonRepo personRepo;
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public Person savePerson(@RequestBody Person person){
		Person personObj = personRepo.save(person);
		return personObj;
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	@ResponseBody
	public Person updatePerson(@RequestBody Person person){
		Person personObj = personRepo.fetchById(person.getId());
		if(personObj != null){
			personRepo.delete(personObj);
		}
		personObj = personRepo.save(person);
		return personObj;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public List<Person> getPersons(){
		List<Person> listPerson = new ArrayList<>();
		personRepo.findAll().forEach(e -> listPerson.add(e));
		return listPerson;
		
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	@ResponseBody
	public Person getPersonById(@PathVariable String id){
		Person person = personRepo.fetchById(id);
		return person;
		
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public void deletePersonById(@PathVariable String id){
		Person person = personRepo.fetchById(id);
		if(person != null){
			personRepo.delete(person);
		}
	}
	
	@RequestMapping(value = "/hello", method=RequestMethod.GET)
	public String getName(){
		return "Hello from test method";
	}

}
