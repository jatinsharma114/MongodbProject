package com.test.mongodbTest.Repository;

import com.test.mongodbTest.Model.Club;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerContainerRepo extends MongoRepository<Club, String> {
    @Query("{ 'partners.age': ?0 }")
    List<Club> findByPartnerAge(Integer age);

}
