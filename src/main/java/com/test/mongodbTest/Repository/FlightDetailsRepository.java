package com.test.mongodbTest.Repository;

import com.test.mongodbTest.Model.FlightDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FlightDetailsRepository extends MongoRepository<FlightDetails, String> {
    // You can add custom query methods if needed
}
