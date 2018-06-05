package com.techm.smart.parking.solution.utils;

public class ParkInput {
	
	private String tagAddress;
	
	private String deviceId;
	
	private String time;
	
	private String collectorId;

	public String getTagAddress() {
		return tagAddress;
	}

	public void setTagAddress(String tagAddress) {
		this.tagAddress = tagAddress;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCollectorId() {
		return collectorId;
	}

	public void setCollectorId(String collectorId) {
		this.collectorId = collectorId;
	}

	@Override
	public String toString() {
		return "ParkInput [tagAddress=" + tagAddress + ", deviceId=" + deviceId + ", time=" + time + ", collectorId="
				+ collectorId + "]";
	}

}
