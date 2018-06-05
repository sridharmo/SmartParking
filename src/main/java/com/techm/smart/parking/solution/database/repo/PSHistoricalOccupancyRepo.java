package com.techm.smart.parking.solution.database.repo;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import com.techm.smart.parking.solution.domain.PSHistoricalOccupancy;

public interface PSHistoricalOccupancyRepo extends CassandraRepository<PSHistoricalOccupancy>{

	@Query("select * from ParkingSlotHistoricalOccupancy where parkingSlotId=?0")
	public PSHistoricalOccupancy fetchById(String parkingSlotId);
}
