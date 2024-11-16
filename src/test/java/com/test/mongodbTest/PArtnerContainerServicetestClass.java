package com.test.mongodbTest;

import com.test.mongodbTest.Model.Club;
import com.test.mongodbTest.Repository.PartnerContainerRepo;
import com.test.mongodbTest.Service.ClubRepositoryCustomImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
//@AutoConfigureMockMvc
public class PArtnerContainerServicetestClass {

    @Autowired
    private ClubRepositoryCustomImpl partnerContainerService;

    @MockBean
    private PartnerContainerRepo partnerContainerRepo;

    @Test
    public void addNewPartnerTEST() {

        Club club = new Club();// Object created for Dummy
        // Set fields on partner if necessary

        // Started Mocking
        // Joh m mock ker raha hu voh meko return kro
        // nah ki (mongoDBRepo.save()) per --> REAL m call ho.
        when(partnerContainerRepo.save(any(Club.class))).thenReturn(club);

        // addNewPartner ka Test case basically!! FIRST yahi main imp hai joh check kerna hai.!
        Club result = partnerContainerService.addNewPartner(club);

        assertEquals(club, result);
    }
}
