package com.techm.smart.parking.solution.database.repo;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import com.techm.smart.parking.solution.domain.ParkingSlot;

public interface ParkingSlotRepo extends CassandraRepository<ParkingSlot>{

	@Query("select * from Device_ParkingSlot where deviceId=?0")
	public ParkingSlot fetchByDeviceId(String deviceId);
}
