
package com.techm.smart.parking.solution.domain;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table(value="person")
public class Person  {

    @PrimaryKey
    private String id;

    private String personName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

   

    @Override
    public String toString() {
        return "Person [Id=" + id + ", personName=" + personName + "]";
    }

}
