# parking-lot-system-poc
Java Demo Application with SpringBoot for Parking-Lot API.

## Build + Tests
To build locally and to validate all requirements automatically, run the Unit tests and Functional Tests with:
```
./gradlew clean build
```

## Docker-compose
The Dockerfile is instructed to build the application, create the fat jar and 
let it ready to be used as an image.

To start the mysql & application containers in one shot, just execute:
```
docker-compose up -d
```

 
If there is a need to rebuild the application, just include --build in the command:
```
docker-compose up --build
```

To stop all the containers, execute:
```
docker-compose down
```

## Documentation

The REST API documentation is generated automatically by Swagger 2, 
and it's available at http://localhost:8080//swagger-ui/#/

![Swagger API Screenshot](parking-lot-doc.png?raw=true)


## Notes:
All the project was tested on:

- docker version 20.10.13
- docker-compose version 1.25.0
- openjdk 11.0.13 2021-10-19

---

## Endpoints to validate the requirements

- Tell us how many spots are remaining
  - GET http://localhost:8080/spots/available
  
- Tell us when the parking lot is full
  - GET http://localhost:8080/spots/is-full

- Tell us how many spots vans are taking up
  - GET http://localhost:8080/parking-spots/vehicle-type/VAN

- Take in a vehicle to park
  - POST http://localhost:8080/parking-spots
    - { "code": "xyz", "type": "VAN"}
  
- Remove a vehicle from the lot
  - REMOVE http://localhost:8080/parking-spots/vehicle-code/{code}

---

## Useful endpoints

- List all spots with the current status
  - GET http://localhost:8080/spots/list

- Include extra spots in the last position
  - POST http://localhost:8080/spots
    - { "code": "G8", "type": "CAR"}
