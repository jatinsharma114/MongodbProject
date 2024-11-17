package com.test.mongodbTest.Service;


import com.mongodb.client.result.UpdateResult;
import com.test.mongodbTest.Model.Partner;
import com.test.mongodbTest.Model.Club;
import com.test.mongodbTest.Repository.PartnerContainerRepo;
import com.test.mongodbTest.Utils.GlobalConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClubRepositoryCustomImpl implements CLubRepositoryCustom {

    @Autowired
    private PartnerContainerRepo partnerContainerRepo;

    private final MongoTemplate mongoTemplate;


    public ClubRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Club> findAllPartnerContainers() {
        return partnerContainerRepo.findAll();
    }

    @Override
    public Club addNewPartner(Club club) {
        // Set createdOn for PartnerContainer
        String currentTimestamp = LocalDateTime.now().format(GlobalConstant.FORMATTER);
        club.setCreatedOn(currentTimestamp);

        // Set updatedOn for each Partner in the container
        if (club.getPartners() != null) {
            club.getPartners()
                    .forEach(partner -> partner.setUpdatedOn(currentTimestamp));
        }

        return partnerContainerRepo.save(club);
    }

    @Override
    public Partner findPartnerById(int partnerId) {
        Query queryCreated = new Query();
        queryCreated.addCriteria(Criteria.where("partners._id").is(partnerId));
        
        Club container = mongoTemplate.findOne(queryCreated, Club.class);
        if (container != null) {
            return container.getPartners().stream()
                    .filter(partner -> partner.getId() == partnerId)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    @Override
    public List<Partner> getPartnersByName(String name) {
        // Aggregation Pipeline:
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.unwind("partners"),
                Aggregation.match(Criteria.where("partners.name").regex(name, "i")), // Case-insensitive match
                Aggregation.replaceRoot("partners")
        );

        AggregationResults<Partner> results = mongoTemplate.aggregate(aggregation, "C1", Partner.class);
        return results.getMappedResults();
    }

    @Override
    public ResponseEntity<List<Partner>> updatePartner(Integer partnerId, Partner updatedPartner) {
        // Step 1: Create the Update object
        Update updateForMainContact = new Update();

        // Step 2: Set the fields to be updated
        if(!updatedPartner.getName().isEmpty()) {
            updateForMainContact.set("partners.$.name", updatedPartner.getName());
        }
        if(!updatedPartner.getDetails().isEmpty()) {
            updateForMainContact.set("partners.$.details", updatedPartner.getDetails());
        }

        if(updatedPartner.getAge() != null) {
            updateForMainContact.set("partners.$.age", updatedPartner.getAge());
        }

        // Step 3: Set the updatedOn field to the current date and time
        String currentTimestamp = LocalDateTime.now().format(GlobalConstant.FORMATTER);
        updateForMainContact.set("partners.$.updatedOn", currentTimestamp + " IST");

        // Step 3: Execute the update
        // Using the updateFirst method to update the first matching document
        Query query = new Query(Criteria.where("partners._id").is(partnerId));

        //UPDATE Operation in DB Done!
        UpdateResult result = mongoTemplate.updateFirst(query, updateForMainContact, Club.class);

        // Step 4: Check if the update was successful
        if (result.getMatchedCount() > 0) {
            // Optionally, fetch the updated PartnerContainer to return it
            Club updatedContainer = mongoTemplate.findOne(query, Club.class);
            return ResponseEntity.ok(updatedContainer.getPartners());
        }

        // Step 5: If not found, return a 404 response
        return ResponseEntity.notFound().build();

    }


    //--------------------------------Based on AGE field------------------------------------
    public List<Partner> getPartnersAboveAge(int age) {
        // Aggregation Pipeline ::
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.unwind("partners"),
                Aggregation.match(Criteria.where("partners.age").gt(age)),
                Aggregation.sort(Sort.by(Sort.Order.asc("partners.age"))), // Sort by age ascending
                Aggregation.replaceRoot("partners")
                //After replaceRoot("partners"): The replaceRoot stage changes the structure
                // of each output document to only contain the --> partners fields:
        );

        AggregationResults<Partner> results = mongoTemplate.aggregate(aggregation, "C1", Partner.class);
        return results.getMappedResults();
    }

    /**
     * Here I Need to use the --> Loop :( to fetch the Data
     * But in Aggregation I don't need any loop operation.
     */
    @Override
    public List<Partner> creteriaBasedOnAge(Integer minAge) {
        // Criteria Adding to Query ::
        Query query = new Query();
        query.addCriteria(Criteria.where("partners.age").gt(minAge));

        // Execute the query and return the list of PartnerContainer documents
        List<Club> containers = mongoTemplate.find(query, Club.class, "C1");

        // Extract partners from each container
        List<Partner> filteredPartners = new ArrayList<>();
        for (Club container : containers) {
            for (Partner partner : container.getPartners()) {
                if (partner.getAge() > minAge) {
                    filteredPartners.add(partner);
                }
            }
        }

        return filteredPartners;
    }

    @Override
    public List<Partner> getPartnerContainersWithAgeAbove40() {
        return null;
    }

    @Override
    public List<Club> findByPartnerAge(Integer age) {
        return partnerContainerRepo.findByPartnerAge(age);
    }


}
