package com.techm.smart.parking.solution.domain;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table(value="TagTable")
public class Tag {
	
	@PrimaryKey
	private String carNumber;
	
	private String tagNumber;
	
	private String city;
	
	private String country;
	
	private String state;
	
	private String issueDate;

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getTagNumber() {
		return tagNumber;
	}

	public void setTagNumber(String tagNumber) {
		this.tagNumber = tagNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	@Override
	public String toString() {
		return "Tag [carNumber=" + carNumber + ", tagNumber=" + tagNumber + ", city=" + city + ", country="
				+ country + ", state=" + state + ", issueDate=" + issueDate + "]";
	}
}
