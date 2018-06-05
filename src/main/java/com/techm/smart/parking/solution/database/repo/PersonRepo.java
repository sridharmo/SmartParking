
package com.techm.smart.parking.solution.database.repo;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import com.techm.smart.parking.solution.domain.Person;

public interface PersonRepo extends CassandraRepository<Person> {

	@Query("select * from person where id=?0")
	public Person fetchById(String id);

}
