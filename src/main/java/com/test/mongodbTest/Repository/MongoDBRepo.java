package com.test.mongodbTest.Repository;

import com.test.mongodbTest.Model.Partner;
import com.test.mongodbTest.Model.PartnerContainer;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MongoDBRepo extends MongoRepository<PartnerContainer, String> {

    /**
     * The Partner with age: 44 matches the query ($gte: 40, $lte: 45), so the whole PartnerContainer is returned.
     * @return
     */
    @Query("{ 'partners.age' : { $gte: 40 } }")
    List<Partner> findPartnerContainersByAgeGreaterThan40();
////    db["C1"].find({ "partners.age": { $gte: 40 } })
//

}
