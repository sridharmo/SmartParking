package com.techm.smart.parking.solution.domain;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table(value="Device_ParkingSlot")
public class ParkingSlot {

	@PrimaryKey
	private String deviceId;
	
	private String parkingZone;
	
	private String parkingSlotId;
	
	private String state;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getParkingZone() {
		return parkingZone;
	}

	public void setParkingZone(String parkingZone) {
		this.parkingZone = parkingZone;
	}

	public String getParkingSlotId() {
		return parkingSlotId;
	}

	public void setParkingSlotId(String parkingSlotId) {
		this.parkingSlotId = parkingSlotId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "ParkingSlot [deviceId=" + deviceId + ", parkingZone=" + parkingZone + ", parkingSlotId=" + parkingSlotId
				+ ", state=" + state + "]";
	}
}
