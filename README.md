# Springboot service to find available timeSlots for a person/machine in order to book an appointment

### How to build?
```
mvn install
```

### How to run?
##### Without docker
```
mvn spring-boot:run
```
##### With docker
```
(not implemented yet) docker-compose up
```

### How to debug?
Use the following command to start and execute a remote debugger through IDEA (Execution will stop until debugger is 
started)
```
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
```
### How to deploy?
(not implemented yet) 

### Rest api endpoint
http://localhost:8080/availabletimeslots
QueryParameters:
- List<String> ids: Ids of the calendars to be searched
(Note: Query param length should not exceed 1024 chars. If number of ids is greater than 27 then caller should use partitioning to get rid of exceeded length exception )
- int length:  The length (duration in minutes) of the meeting
- start: string
- end: string
  A period within to find availability (ISO8601 time interval,
2019-03-01T13:00:00Z/2019-05-11T15:30:00Z)
- slot type: string
  Optional argument to the service so that it can search the
availability of a specific time slot type

Example:
http://localhost:8080/availabletimeslots?calendarIds=48cadf26-975e-11e5-b9c2-c8e0eb18c1e9,452dccfc-975e-11e5-bfa5-c8e0eb18c1e9,48644c7a-975e-11e5-a090-c8e0eb18c1e9&length=15&start=2019-04-23T08:15:00&end=2019-04-23T12:30:00
# patientsky-dev-assignment
