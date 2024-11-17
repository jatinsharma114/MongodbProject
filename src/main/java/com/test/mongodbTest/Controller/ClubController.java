package com.test.mongodbTest.Controller;

import com.test.mongodbTest.DTO.ClubDTO;
import com.test.mongodbTest.DTO.PartnerDTO;
import com.test.mongodbTest.Model.Club;
import com.test.mongodbTest.Model.Partner;
import com.test.mongodbTest.Service.CLubRepositoryCustom;
import com.test.mongodbTest.Utils.ClubConverter;
import com.test.mongodbTest.Utils.PartnerConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class ClubController {

    @Autowired
    private CLubRepositoryCustom userService;

    @GetMapping("/getAll")
    public ResponseEntity<List<ClubDTO>> getAllClubs() {
        List<Club> allClubs = userService.findAllPartnerContainers();

        if (allClubs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<ClubDTO> clubDTOs = ClubConverter.convertToDTOList(allClubs);
        return ResponseEntity.ok(clubDTOs);
    }

    @PostMapping("/addnew")
    public ResponseEntity<ClubDTO> addClub(@RequestBody Club club) {
        Club savedClub = userService.addNewPartner(club);
        ClubDTO clubDTO = ClubConverter.convertToDTO(savedClub);
        return ResponseEntity.status(HttpStatus.CREATED).body(clubDTO);
    }

    @GetMapping("/getByID/{id}")
    public ResponseEntity<PartnerDTO> getPartnerById(@PathVariable(value = "id") int id) {
        Partner partner = userService.findPartnerById(id);

        if (partner == null) {
            return ResponseEntity.notFound().build();
        }

        PartnerDTO partnerDTO = PartnerConverter.convertToDTO(partner);
        return ResponseEntity.ok(partnerDTO);
    }

    @GetMapping("/partners/name")
    public ResponseEntity<List<PartnerDTO>> getPartnersByName(@RequestParam String name) {
        List<Partner> partners = userService.getPartnersByName(name);

        if (partners.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<PartnerDTO> partnerDTOs = PartnerConverter.convertToDTOList(partners);
        return ResponseEntity.ok(partnerDTOs);
    }

    @PostMapping("/updatePartner/{id}")
    public ResponseEntity<ResponseEntity<List<Partner>>> updatePartner(@PathVariable int id, @RequestBody Partner updatedPartner) {
        Partner existingPartner = userService.findPartnerById(id);
        if (existingPartner == null) {
            return ResponseEntity.notFound().build();
        }

        ResponseEntity<List<Partner>> partner = userService.updatePartner(id, updatedPartner);

        if (partner != null) {
            return ResponseEntity.ok(partner);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/partners/age")
    public ResponseEntity<List<PartnerDTO>> getPartnersAboveAge(@RequestParam int age) {
        List<Partner> partners = userService.getPartnersAboveAge(age);

        if (partners.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<PartnerDTO> partnerDTOs = PartnerConverter.convertToDTOList(partners);
        return ResponseEntity.ok(partnerDTOs);
    }

    @GetMapping("/partners/getByAge")
    public ResponseEntity<List<PartnerDTO>> getPartnersByAge(@RequestParam Integer age) {
        List<Partner> partners = userService.creteriaBasedOnAge(age);

        if (partners.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<PartnerDTO> partnerDTOs = PartnerConverter.convertToDTOList(partners);
        return ResponseEntity.ok(partnerDTOs);
    }
}
