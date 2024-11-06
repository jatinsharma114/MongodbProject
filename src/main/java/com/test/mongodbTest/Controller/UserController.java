package com.test.mongodbTest.Controller;


import com.test.mongodbTest.Model.Partner;
import com.test.mongodbTest.Model.PartnerContainer;
import com.test.mongodbTest.Service.PartnerContainerRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private PartnerContainerRepositoryCustom userService;

    public UserController() {
    }

    @GetMapping("/getAll")
    public List<PartnerContainer> getAllPosts() {
        return userService.findAllPartnerContainers();
    }

    @GetMapping("/getByID/{id}")
    public ResponseEntity<Partner> getPartnerById(@PathVariable("id") int id) {

        Partner partner = userService.findPartnerById(id);// external Query Used

        if (partner != null) {
            return ResponseEntity.ok(partner);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/partners/name")
    public List<Partner> getPartnersByName(@RequestParam String name) {
        return userService.getPartnersByName(name);
    }


    @PostMapping("/updatePartner/{id}")
    public ResponseEntity<ResponseEntity<List<Partner>>> updatePartner(@PathVariable int id, @RequestBody Partner updatedPartner) {

        ResponseEntity<List<Partner>> partner = userService.updatePartner(id, updatedPartner);

        if (partner != null) {
            return ResponseEntity.ok(partner);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/addnew")
    public ResponseEntity<PartnerContainer> addPartnerContainer(@RequestBody PartnerContainer partnerContainer) {
        PartnerContainer savedContainer = userService.addNewPartner(partnerContainer);
        return ResponseEntity.ok(savedContainer);
    }

    // Aggregation pipeline ::
    @GetMapping("/partners/age")
    public List<Partner> getPartnersAboveAge(@RequestParam int age) {
        return userService.getPartnersAboveAge(age);
    }

    // Criteria based: Using the Loop to fetch @Partner from @PartnerContainer
    @GetMapping("/partners/getByAge")
    public ResponseEntity<List<Partner>> creteriaBasedOnAge(@RequestParam Integer age) {
        List<Partner> partners = userService.creteriaBasedOnAge(age);
        if (!partners.isEmpty()) {
            return ResponseEntity.ok(partners);
        }
        return ResponseEntity.notFound().build();
    }

}
