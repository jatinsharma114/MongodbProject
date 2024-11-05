package com.test.mongodbTest.Service;

import com.test.mongodbTest.Model.Partner;
import com.test.mongodbTest.Model.PartnerContainer;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PartnerContainerRepositoryCustom {

    Partner findPartnerById(int partnerId);
    Partner conditionCreteria(String name);

    ResponseEntity<List<Partner>> getPartnersByCondition(String name);

    ResponseEntity<List<Partner>> updatePartner(Integer name, Partner updatedPartner);

    ResponseEntity<List<Partner>> pipineResult();

    public PartnerContainer setCreationDatePartnerContainer(PartnerContainer partnerContainer);

}
