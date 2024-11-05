package com.test.mongodbTest.Service;


import com.mongodb.client.result.UpdateResult;
import com.test.mongodbTest.Model.Partner;
import com.test.mongodbTest.Model.PartnerContainer;
import com.test.mongodbTest.Utils.GlobalConstant;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PartnerContainerRepositoryCustomImpl implements PartnerContainerRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public PartnerContainerRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Partner findPartnerById(int partnerId) {
        Query queryCreated = new Query();
        queryCreated.addCriteria(Criteria.where("partners._id").is(partnerId));
        
        PartnerContainer container = mongoTemplate.findOne(queryCreated, PartnerContainer.class);
        if (container != null) {
            return container.getPartners().stream()
                    .filter(partner -> partner.getId() == partnerId)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    /**
     * Each method has its strengths, and the choice between them will depend on the specific requirements of your use case.
     * If you have further questions or need clarification on any aspect, feel free to ask!
     * @param name
     * @param details
     * @return
     */
    @Override
    public Partner conditionCreteria(String name) {
        // Create criteria based on parameters
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("partners.name").is(name)
        );

        Query queryCreated = new Query(criteria);
            PartnerContainer container = mongoTemplate.findOne(queryCreated, PartnerContainer.class);

            if (container != null) {
                return container.getPartners().stream()
                        .filter(partner -> partner.getName().equals(name))
                        .findFirst()
                        .orElse(null);
        }
        return null;
    }

    @Override
    public ResponseEntity<List<Partner>> getPartnersByCondition(@RequestParam String name) {
        // Step 1: Define your criteria Defined the age should be greater than something like this here!
        Criteria criteria = Criteria.where("partners.name").is(name);

        // Step 2: Create the MatchOperation
        MatchOperation matchOperation = Aggregation.match(criteria);

        LimitOperation limit = Aggregation.limit(2);

        // Step 3: Build your aggregation pipeline
        Aggregation aggregation = Aggregation.newAggregation(matchOperation, limit);


        // Step 4: Execute the aggregation query
        List<PartnerContainer> results = mongoTemplate.aggregate(aggregation, "C1", PartnerContainer.class).getMappedResults();

        // Flatten the results and extract partners
        List<Partner> partners = results.stream()
                .flatMap(container -> container.getPartners().stream())
                .toList();

        // Return the matched partners
        if (!partners.isEmpty()) {
            return ResponseEntity.ok(partners);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     *
     * @param partnerId
     * @param updatedPartner
     * @return List<Partner> With the Updated Single Partner.
     */
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
        UpdateResult result = mongoTemplate.updateFirst(query, updateForMainContact, PartnerContainer.class);

        // Step 4: Check if the update was successful
        if (result.getMatchedCount() > 0) {
            // Optionally, fetch the updated PartnerContainer to return it
            PartnerContainer updatedContainer = mongoTemplate.findOne(query, PartnerContainer.class);
            return ResponseEntity.ok(updatedContainer.getPartners());
        }

        // Step 5: If not found, return a 404 response
        return ResponseEntity.notFound().build();

    }

    @Override
    public ResponseEntity<List<Partner>> pipineResult() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("partners.age").lt(25)), // Filter for age < 25
                Aggregation.group("partners.age") // Group only by age
                        .addToSet("partners.name").as("names"), // Add the names to the group
                Aggregation.sort(Sort.by(Sort.Order.asc("age"))), // Sort by age
                Aggregation.limit(3) // Limit to 3 results
        );

        // Execute the aggregation query
        List<PartnerContainer> results = mongoTemplate.aggregate(aggregation, "C1", PartnerContainer.class).getMappedResults();

        // Flatten the results and extract partners
        List<Partner> partners = results.stream()
                .flatMap(container -> container.getPartners().stream())
                .filter(partner -> partner.getAge() < 25) // Ensure we only get partners under 25
                .toList();

        // Return the matched partners
        if (!partners.isEmpty()) {
            return ResponseEntity.ok(partners);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public PartnerContainer setCreationDatePartnerContainer(PartnerContainer partnerContainer) {
        // Set createdOn for PartnerContainer
        String currentTimestamp = LocalDateTime.now().format(GlobalConstant.FORMATTER);
        partnerContainer.setCreatedOn(currentTimestamp);

        // Set updatedOn for each Partner in the container
        if (partnerContainer.getPartners() != null) {
            partnerContainer.getPartners()
                            .forEach(partner -> partner.setUpdatedOn(currentTimestamp));
        }

        return partnerContainer;
    }



}
