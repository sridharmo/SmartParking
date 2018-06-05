package com.techm.smart.parking.solution.database.repo;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import com.techm.smart.parking.solution.domain.Tag;


public interface TagRepo extends CassandraRepository<Tag> {

	@Query("select * from TagTable where tagNumber=?0")
	public Tag fetchByTagNumber(String tagNumber);
}
