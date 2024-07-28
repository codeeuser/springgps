# Simulate Live GPS Data for Testing

* Produce the Live Navigation GPS data based on the CSV file built with Spring Boot.
* The Live GPS data would repeat after end of the CSV file.

## Sample Access URL

http://localhost:8087/api/coordinate

## Sample Returned Data Format

- Return JSON format data
- {"type":"Point","coordinates":{"lng":101.6869,"lat":3.139}}

## CLI

- ./mvnw spring-boot:run
- ./mvnw clean package
- java -jar springgps-0.0.1-SNAPSHOT.jar
