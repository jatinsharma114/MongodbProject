package com.test.mongodbTest.ClubControllerTest;

import com.test.mongodbTest.Model.Club;
import com.test.mongodbTest.Model.Partner;
import com.test.mongodbTest.Repository.PartnerContainerRepo;
import com.test.mongodbTest.Service.ClubRepositoryCustomImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.bson.Document;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ClubRepositoryCustomImpl.class})
public class ClubRepositoryCustomImplTest {

    @MockBean
    private PartnerContainerRepo partnerContainerRepo;

    @MockBean
    private MongoTemplate mongoTemplate;

    @Autowired
    private ClubRepositoryCustomImpl clubRepositoryCustom;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllPartnerContainers() {
        // Mock repository behavior
        List<Club> mockClubs = Arrays.asList(
                Club.builder().otherField("Club 1").build(),
                Club.builder().otherField("Club 2").build()
        );
        when(partnerContainerRepo.findAll()).thenReturn(mockClubs);

        // Call the method
        List<Club> result = clubRepositoryCustom.findAllPartnerContainers();

        // Assertions
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Club 1", result.get(0).getOtherField());
        assertEquals("Club 2", result.get(1).getOtherField());

        // Verify interaction
        verify(partnerContainerRepo, times(1)).findAll();
    }

    @Test
    public void testAddNewPartner() {
        // Mock data
        Club club = Club.builder()
                .otherField("New Club")
                .partners(Collections.emptyList())
                .build();

        Club savedClub = Club.builder()
                .otherField("New Club")
                .partners(Collections.emptyList())
                .createdOn("2024-11-18")
                .build();

        when(partnerContainerRepo.save(club)).thenReturn(savedClub);

        // Call the method
        Club result = clubRepositoryCustom.addNewPartner(club);

        // Assertions
        assertNotNull(result);
        assertEquals("New Club", result.getOtherField());
        assertEquals("2024-11-18", result.getCreatedOn());

        // Verify interaction
        verify(partnerContainerRepo, times(1)).save(club);
    }

    @Test
    public void testFindPartnerById() {
        // Mock data
        Partner partner = Partner.builder().id(1).name("John").build();
        Club club = Club.builder()
                .partners(Collections.singletonList(partner))
                .build();

        Query query = new Query();
        query.addCriteria(Criteria.where("partners._id").is(1));

        when(mongoTemplate.findOne(query, Club.class)).thenReturn(club);

        // Call the method
        Partner result = clubRepositoryCustom.findPartnerById(1);

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.getId().intValue());
        assertEquals("John", result.getName());

        // Verify interaction
        verify(mongoTemplate, times(1)).findOne(query, Club.class);
    }

    @Test
    public void testGetPartnersByName() {
        // Mock data
        Partner partner = Partner.builder().id(1).name("John").build();

        // Mock result
        AggregationResults<Partner> mockResults = new AggregationResults<>(
                Collections.singletonList(partner),
                new Document()
        );

        // Mock behavior
        when(mongoTemplate.aggregate(any(Aggregation.class), eq("C1"), eq(Partner.class)))
                .thenReturn(mockResults);

        // Call the method
        List<Partner> result = clubRepositoryCustom.getPartnersByName("John");

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getName());

        // Verify interactions
        verify(mongoTemplate, times(1)).aggregate(any(Aggregation.class), eq("C1"), eq(Partner.class));
    }

}
