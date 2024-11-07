package com.test.mongodbTest.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@Document(collection = "flightDetails")
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightDetails {

    @Id
    private String flightId; // Unique identifier for the flight
    private String airline; // Airline operating the flight
    private String flightNumber; // Flight number
    private String departureLocation; // Departure location
    private String arrivalLocation; // Arrival location
    private Date departureTime; // Departure date and time
    private Date arrivalTime; // Arrival date and time
    private String gateNumber; // Gate number for boarding
}
