package com.techm.smart.parking.solution.database.repo;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import com.techm.smart.parking.solution.domain.PSLiveOccupancy;

public interface PSLiveOccupancyRepo extends CassandraRepository<PSLiveOccupancy>{

	@Query("select * from ParkingSlotLiveOccupancy where parkingSlotId=?0")
	public PSLiveOccupancy fetchById(String parkingSlotId);
}
