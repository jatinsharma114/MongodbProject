package com.test.mongodbTest.Repository;

import com.test.mongodbTest.Model.FlightDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface FlightDetailsRepository extends MongoRepository<FlightDetails, String> {
    // You can add custom query methods if needed


    //db["flightDetails"].find({ 'departureLocation' : 'New York (JFK)' })
//    @Query("{ 'departureLocation' : 'New York (JFK)' }")
    @Query("{ '$or': [ { 'departureLocation': 'New York (JFK)' }, { 'departureLocation': 'Mumbai' } ] }")
    List<FlightDetails> findFlightsByDepartureLocation();
}
