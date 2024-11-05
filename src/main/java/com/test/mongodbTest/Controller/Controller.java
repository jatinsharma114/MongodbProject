package com.test.mongodbTest.Controller;


import com.test.mongodbTest.Model.Partner;
import com.test.mongodbTest.Model.PartnerContainer;
import com.test.mongodbTest.Repository.MongoDBRepo;
import com.test.mongodbTest.Service.PartnerContainerRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    MongoDBRepo mongoDBRepo;

    @Autowired
    private PartnerContainerRepositoryCustom mongoQuery; // Inject the repository

    public Controller() {
    }

    @GetMapping("/getAll")
    public List<PartnerContainer> getAllPosts() {
        List<PartnerContainer> all = mongoDBRepo.findAll();
        return all;
    }

    @GetMapping("/getByID/{id}")
    public ResponseEntity<Partner> getPartnerById(@PathVariable("id") int id) {

        Partner partner = mongoQuery.findPartnerById(id);// external Query Used

        if (partner != null) {
            return ResponseEntity.ok(partner);
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/getByCondition1")
    public ResponseEntity<Partner> conditionBased1(@RequestParam String name) {
        Partner partner = mongoQuery.conditionCreteria(name);

        if (partner != null) {
            return ResponseEntity.ok(partner);
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/getByCondition2")
    public ResponseEntity<ResponseEntity<List<Partner>>> conditionBased2(@RequestParam String name) {
        ResponseEntity<List<Partner>> partner = mongoQuery.getPartnersByCondition(name);

        if (partner != null) {
            return ResponseEntity.ok(partner);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/pipeline")
    public ResponseEntity<ResponseEntity<List<Partner>>> pipeline() {
        ResponseEntity<List<Partner>> partner = mongoQuery.pipineResult();

        if (partner != null) {
            return ResponseEntity.ok(partner);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/updatePartner/{id}")
    public ResponseEntity<ResponseEntity<List<Partner>>> updatePartner(@PathVariable int id, @RequestBody Partner updatedPartner) {

        ResponseEntity<List<Partner>> partner = mongoQuery.updatePartner(id, updatedPartner);

        if (partner != null) {
            return ResponseEntity.ok(partner);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/addnew")
    public ResponseEntity<PartnerContainer> addPartnerContainer(@RequestBody PartnerContainer partnerContainer) {
        PartnerContainer savedContainer = mongoDBRepo.save(mongoQuery.setCreationDatePartnerContainer(partnerContainer));
        return ResponseEntity.ok(savedContainer);
    }


}
