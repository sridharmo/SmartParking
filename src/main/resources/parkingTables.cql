
-- Cassandra .cql specification

-- Command to import file SOURCE 'tables_mysql.cql'


DROP KEYSPACE  IF EXISTS smartParking;

CREATE KEYSPACE smartParking
 WITH replication = {'class':'SimpleStrategy',
'replication_factor' : 3};


USE smartParking;

DROP TABLE IF EXISTS smartParking.TagTable;
DROP TABLE IF EXISTS smartParking.ParkingZoneMapping;
DROP TABLE IF EXISTS smartParking.Device_ParkingSlot;
DROP TABLE IF EXISTS smartParking.ParkingSlotLiveOccupancy;
DROP TABLE IF EXISTS smartParking.ParkingSlotHistoricalOccupancy;
DROP TABLE IF EXISTS smartParking.person;


CREATE TABLE smartParking.TagTable(
	tagNumber text PRIMARY KEY,
	carNumber text, 
	city text,
	country text,
	issueDate text,
	state text,
); 

CREATE TABLE smartParking.ParkingZoneMapping(
	pkZoneId text PRIMARY KEY,
	area text,
	city text,
	latitude decimal,
	longitude decimal,
	collectorId text,
	ownerOfParkingZone text,	
	parkingSlotIdList list<text>	
); 

CREATE TABLE smartParking.Device_ParkingSlot(
	deviceId text PRIMARY KEY,
	parkingZone text,
	parkingSlotId text,
	state  text 	
); 


CREATE TABLE smartParking.ParkingSlotLiveOccupancy(
 	parkingSlotId text  PRIMARY KEY,	
	parkingZone text,
	carNumber text ,
	collectorId text,
	startTime  timestamp,
	endTime  timestamp 	
); 

CREATE TABLE smartParking.ParkingSlotHistoricalOccupancy(	
	parkingSlotId text  PRIMARY KEY,	
	parkingZone text,
	carNumber text ,
	collectorId text,
	startTime  timestamp,
	endTime  timestamp	
); 

CREATE TABLE smartParking.person(
	id text PRIMARY KEY,
	personName text
);
