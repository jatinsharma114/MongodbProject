package com.test.mongodbTest.Repository;

import com.test.mongodbTest.Model.PartnerContainer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoDBRepo extends MongoRepository<PartnerContainer, String> {

}
