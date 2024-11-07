package com.test.mongodbTest.Service;

import com.test.mongodbTest.Model.Partner;
import com.test.mongodbTest.Model.PartnerContainer;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface PartnerContainerRepositoryCustom {

    List<PartnerContainer> findAllPartnerContainers();

    Partner findPartnerById(int partnerId);

    //Similar name of the partner.
   List<Partner> getPartnersByName(String name);

   // Add New PartnerContainer with multi. Partners
    public PartnerContainer addNewPartner(PartnerContainer partnerContainer);

    /**
     * Update the Partner field based on the partnerId
     * @param partnerId
     * @param updatedPartner
     * @return
     */
    ResponseEntity<List<Partner>> updatePartner(Integer partnerId, Partner updatedPartner);


    //----------------------By AGE ----------------------------
    public List<Partner> getPartnersAboveAge(int age);

    List<Partner> creteriaBasedOnAge(Integer age);


    //-------------------
    public List<Partner> getPartnerContainersWithAgeAbove40();

}
