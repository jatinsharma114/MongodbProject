package com.test.mongodbTest.Service;

import com.test.mongodbTest.Model.Partner;
import com.test.mongodbTest.Model.Club;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CLubRepositoryCustom {

    List<Club> findAllPartnerContainers();

    Partner findPartnerById(int partnerId);

    //Similar name of the partner.
   List<Partner> getPartnersByName(String name);

   // Add New PartnerContainer with multi. Partners
    public Club addNewPartner(Club club);

    /**
     * Update the Partner field based on the partnerId
     * @param partnerId
     * @param updatedPartner
     * @return
     */
    ResponseEntity<List<Partner>> updatePartner(Integer partnerId, Partner updatedPartner);


    //----------------------By AGE ----------------------------
    public List<Partner> getListOfPartnersBasedOnAge(int age);

    List<Partner> creteriaBasedOnAge(Integer age);


    //-------------------
    public List<Partner> getPartnerContainersWithAgeAbove40();


    List<Club> findByPartnerAge(Integer age);


}
