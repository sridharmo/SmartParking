package com.techm.smart.parking.solution.domain;

import java.util.List;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table(value="ParkingZoneMapping")
public class ParkingZone {

	@PrimaryKey
	private String pkZoneId;
	
	private String city;
	
	private String area;
	
	private String controllerId;
	
	private Double latitude;
	
	private Double longitude;
	
	@Column(value = "ownerOfParkingZone")
	private String ownerPkZone;
	
	@Column(value = "parkingSlotIdList")
	List<String> listParkingSlots;

	public String getPkZoneId() {
		return pkZoneId;
	}

	public void setPkZoneId(String pkZoneId) {
		this.pkZoneId = pkZoneId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getControllerId() {
		return controllerId;
	}

	public void setControllerId(String controllerId) {
		this.controllerId = controllerId;
	}
	
	public Double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	public Double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getOwnerPkZone() {
		return ownerPkZone;
	}

	public void setOwnerPkZone(String ownerPkZone) {
		this.ownerPkZone = ownerPkZone;
	}

	public List<String> getListParkingSlots() {
		return listParkingSlots;
	}

	public void setListParkingSlots(List<String> listParkingSlots) {
		this.listParkingSlots = listParkingSlots;
	}

	@Override
	public String toString() {
		return "ParkingZone [pkZoneId=" + pkZoneId + ", city=" + city + ", area=" + area + ", controllerId="
				+ controllerId + ", latitude=" + latitude + ", longitude=" + longitude + ", ownerPkZone=" + ownerPkZone
				+ ", listParkingSlots=" + listParkingSlots + "]";
	}
}
