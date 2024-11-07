package com.test.mongodbTest.Service;

import com.test.mongodbTest.Model.FlightDetails;
import com.test.mongodbTest.Repository.FlightDetailsRepository;
import com.test.mongodbTest.Utils.UtilityMethodsForFlight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FlightDetailsService {

    @Autowired
    private FlightDetailsRepository flightDetailsRepository;

    public FlightDetails saveFlightDetails(FlightDetails flightDetails) {
        // Convert IST date string to Date object in UTC
        String departureTimeString = String.valueOf(flightDetails.getDepartureTime());
        if (departureTimeString != null) {
            flightDetails.setDepartureTime(UtilityMethodsForFlight.convertIstToUtc(departureTimeString));
        }

        // Calculate arrival time (2 hours after departure)
        Date departureTime = flightDetails.getDepartureTime();
        Date arrivalTime = calculateArrivalTime(departureTime);
        flightDetails.setArrivalTime(arrivalTime);

        //Save In DB
        return flightDetailsRepository.save(flightDetails);
    }

    //Static logic
    private Date calculateArrivalTime(Date departureTime) {
        if (departureTime == null) return null;
        long twoHoursInMillis = 2 * 60 * 60 * 1000; // 2 hours in milliseconds
        return new Date(departureTime.getTime() + twoHoursInMillis);
    }



}
