package com.test.mongodbTest.Controller;

import com.test.mongodbTest.Model.FlightDetails;
import com.test.mongodbTest.Service.FlightDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/flight")
public class FlightController {

    @Autowired
    private FlightDetailsService flightDetailsService;

    @PostMapping("/save")
    public ResponseEntity<FlightDetails> saveFlightDetails(@RequestBody FlightDetails flightDetails) {
        FlightDetails savedFlightDetails = flightDetailsService.saveFlightDetails(flightDetails);
        return ResponseEntity.ok(savedFlightDetails);
    }
}
