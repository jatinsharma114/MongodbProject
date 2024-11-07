package com.test.mongodbTest.Controller;

import com.test.mongodbTest.Model.FlightDetails;
import com.test.mongodbTest.Repository.FlightDetailsRepository;
import com.test.mongodbTest.Service.FlightDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flight")
public class FlightController {

    @Autowired
    private FlightDetailsService flightDetailsService;

    @Autowired
    FlightDetailsRepository flightDetailsRepository;

    @PostMapping("/save")
    public ResponseEntity<FlightDetails> saveFlightDetails(@RequestBody FlightDetails flightDetails) {
        FlightDetails savedFlightDetails = flightDetailsService.saveFlightDetails(flightDetails);
        return ResponseEntity.ok(savedFlightDetails);
    }

    @GetMapping("/getNewYorkORMumbai")
    public List<FlightDetails> getFlightsFromJFK() {
        return flightDetailsRepository.findFlightsByDepartureLocation();
    }

    //gorup
    //matchOperation
    //sort
    // -- pass Aggregation



}
