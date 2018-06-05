package com.techm.smart.parking.solution.domain;

import java.util.Date;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.mapping.Table;

@Table(value="ParkingSlotLiveOccupancy")
public class PSLiveOccupancy {
	
	@PrimaryKeyColumn(value = "parkingSlotId", type=PrimaryKeyType.PARTITIONED)
	private String pkSlotId;
	
	@Column(value = "parkingZone")
	private String pkZone;
	
	private String carNumber;
	
	private String collectorId;
	
	private Date startTime;
	
	private Date endTime;

	public String getPkSlotId() {
		return pkSlotId;
	}

	public void setPkSlotId(String pkSlotId) {
		this.pkSlotId = pkSlotId;
	}

	public String getPkZone() {
		return pkZone;
	}

	public void setPkZone(String pkZone) {
		this.pkZone = pkZone;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getCollectorId() {
		return collectorId;
	}

	public void setCollectorId(String collectorId) {
		this.collectorId = collectorId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "PSLiveOccupancy [pkSlotId=" + pkSlotId + ", pkZone=" + pkZone + ", carNumber=" + carNumber
				+ ", collectorId=" + collectorId + ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}

}
