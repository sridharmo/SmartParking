package com.techm.smart.parking.solution.database.repo;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import com.techm.smart.parking.solution.domain.ParkingZone;


public interface ParkingZoneRepo extends CassandraRepository<ParkingZone> {

	@Query("select * from ParkingZoneMapping where pkZoneId=?0")
	public ParkingZone fetchByPkZoneId(String pkZoneId);
}
