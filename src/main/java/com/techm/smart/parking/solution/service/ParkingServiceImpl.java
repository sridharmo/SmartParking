package com.techm.smart.parking.solution.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techm.smart.parking.solution.database.repo.PSLiveOccupancyRepo;
import com.techm.smart.parking.solution.database.repo.ParkingSlotRepo;
import com.techm.smart.parking.solution.database.repo.ParkingZoneRepo;
import com.techm.smart.parking.solution.database.repo.TagRepo;
import com.techm.smart.parking.solution.domain.PSLiveOccupancy;
import com.techm.smart.parking.solution.domain.ParkingSlot;
import com.techm.smart.parking.solution.domain.ParkingZone;
import com.techm.smart.parking.solution.domain.Tag;
import com.techm.smart.parking.solution.exception.ServiceException;
import com.techm.smart.parking.solution.utils.ParkInput;
import com.techm.smart.parking.solution.utils.ParkingConstant;

@Service
public class ParkingServiceImpl implements ParkingService {

	@Autowired
	private ParkingSlotRepo parkingSlotRepo;

	@Autowired
	private ParkingZoneRepo parkingZoneRepo;

	@Autowired
	private TagRepo tagRepo;

	@Autowired
	private PSLiveOccupancyRepo psLiveOccupancyRepo;

	private Map<String, TimeZone> collectorTimeZoneMapping = new ConcurrentHashMap<>();

	private SimpleDateFormat timeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	@Override
	public String park(ParkInput parkInput) {
		String strTagNumber = parkInput.getTagAddress();
		String strDeviceId = parkInput.getDeviceId();
		//UUID uuidTag = UUID.fromString(strTagNumber);
		//UUID uuidDeviceId = UUID.fromString(strDeviceId);
		Tag tag = tagRepo.fetchByTagNumber(strTagNumber);
		ParkingSlot pkSlot = parkingSlotRepo.fetchByDeviceId(strDeviceId);
		ParkingZone pkZone = parkingZoneRepo.fetchByPkZoneId(pkSlot.getParkingZone());

		PSLiveOccupancy psLiveOccupancy = new PSLiveOccupancy();
		psLiveOccupancy.setCarNumber(tag.getCarNumber());
		psLiveOccupancy.setPkSlotId(pkSlot.getParkingSlotId());
		psLiveOccupancy.setPkZone(pkSlot.getParkingZone());
		psLiveOccupancy.setCollectorId(pkZone.getControllerId());

		try {
			TimeZone timeZone = collectorTimeZoneMapping.get(pkZone.getControllerId());
			Calendar cal = Calendar.getInstance(timeZone);
			cal.setTime(timeFormat.parse(parkInput.getTime()));
			psLiveOccupancy.setStartTime(cal.getTime());
			cal.add(Calendar.MINUTE, ParkingConstant.PARK_TIME);
			psLiveOccupancy.setEndTime(cal.getTime());
			psLiveOccupancyRepo.save(psLiveOccupancy);

			String msg = tag.getCarNumber() + " is parked on " + pkSlot.getParkingSlotId() + " at "
					+ timeFormat.format(cal.getTime());
			return msg;
		} catch (Exception exp) {
			throw new ServiceException(exp.getMessage());
		}
	}

	@Override
	public String unpark(ParkInput parkInput) {
		String strDeviceId = parkInput.getDeviceId();
		//UUID uuidDeviceId = UUID.fromString(strDeviceId);
		ParkingSlot pkSlot = parkingSlotRepo.fetchByDeviceId(strDeviceId);
		ParkingZone pkZone = parkingZoneRepo.fetchByPkZoneId(pkSlot.getParkingZone());
		PSLiveOccupancy psLiveOccupancy = psLiveOccupancyRepo.fetchById(pkSlot.getParkingSlotId());

		try {
			TimeZone timeZone = collectorTimeZoneMapping.get(pkZone.getControllerId());
			Calendar cal = Calendar.getInstance(timeZone);
			cal.setTime(timeFormat.parse(parkInput.getTime()));
			psLiveOccupancy.setEndTime(cal.getTime());

			psLiveOccupancyRepo.save(psLiveOccupancy);

			String msg = psLiveOccupancy.getCarNumber() + " is unparked from " + pkSlot.getParkingSlotId() + " at "
					+ timeFormat.format(cal.getTime());
			return msg;
		} catch (Exception exp) {
			throw new ServiceException(exp.getMessage());
		}
	}

	@Override
	public String extendPark(ParkInput parkInput) {
		String strDeviceId = parkInput.getDeviceId();
		//UUID uuidDeviceId = UUID.fromString(strDeviceId);
		ParkingSlot pkSlot = parkingSlotRepo.fetchByDeviceId(strDeviceId);
		ParkingZone pkZone = parkingZoneRepo.fetchByPkZoneId(pkSlot.getParkingZone());
		PSLiveOccupancy psLiveOccupancy = psLiveOccupancyRepo.fetchById(pkSlot.getParkingSlotId());

		try {
			TimeZone timeZone = collectorTimeZoneMapping.get(pkZone.getControllerId());
			Calendar cal = Calendar.getInstance(timeZone);
			cal.setTime(psLiveOccupancy.getEndTime());
			cal.add(Calendar.MINUTE, ParkingConstant.TIME_TO_EXTEND);
			psLiveOccupancy.setEndTime(cal.getTime());

			psLiveOccupancyRepo.save(psLiveOccupancy);

			String msg = psLiveOccupancy.getCarNumber() + " is unparked from " + pkSlot.getParkingSlotId() + " at "
					+ timeFormat.format(cal.getTime());
			return msg;
		} catch (Exception exp) {
			throw new ServiceException(exp.getMessage());
		}
	}

	@Override
	public void setControllerTimeZone(String ctrlId, TimeZone timeZone) {
		collectorTimeZoneMapping.put(ctrlId, timeZone);
	}

}
