package com.test.mongodbTest.Controller;

import com.test.mongodbTest.DTO.ClubResponseDTO;
import com.test.mongodbTest.DTO.PartnerResponseDTO;
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
    public ResponseEntity<List<ClubResponseDTO>> getAllClubs() {
        List<Club> allClubs = userService.findAllPartnerContainers();

        if (allClubs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<ClubResponseDTO> clubResponseDTOS = ClubConverter.convertToDTOList(allClubs);
        return ResponseEntity.ok(clubResponseDTOS);
    }

    @PostMapping("/addnew")
    public ResponseEntity<ClubResponseDTO> addClub(@RequestBody Club club) {
        Club savedClub = userService.addNewPartner(club);
        ClubResponseDTO clubResponseDTO = ClubConverter.convertToDTO(savedClub);
        return ResponseEntity.status(HttpStatus.CREATED).body(clubResponseDTO);
    }

    @GetMapping("/getByID/{id}")
    public ResponseEntity<PartnerResponseDTO> getPartnerById(@PathVariable(value = "id") int id) {
        Partner partner = userService.findPartnerById(id);

        if (partner == null) {
            return ResponseEntity.notFound().build();
        }

        PartnerResponseDTO partnerResponseDTO = PartnerConverter.convertToDTO(partner);
        return ResponseEntity.ok(partnerResponseDTO);
    }

    @GetMapping("/partners/name")
    public ResponseEntity<List<PartnerResponseDTO>> getPartnersByName(@RequestParam String name) {
        List<Partner> partners = userService.getPartnersByName(name);

        if (partners.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<PartnerResponseDTO> partnerResponseDTOS = PartnerConverter.convertToDTOList(partners);
        return ResponseEntity.ok(partnerResponseDTOS);
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

    @GetMapping("/partners/getListOfPartnersBasedOnAge")
    public ResponseEntity<List<PartnerResponseDTO>> getListOfPartnersBasedOnAge(@RequestParam int age) {
        List<Partner> partners = userService.getListOfPartnersBasedOnAge(age);

        if (partners.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<PartnerResponseDTO> partnerResponseDTOS = PartnerConverter.convertToDTOList(partners);
        return ResponseEntity.ok(partnerResponseDTOS);
    }

    @GetMapping("/partners/greaterThanRequiredAge")
    public ResponseEntity<List<PartnerResponseDTO>> greaterThanRequiredAge(@RequestParam Integer age) {
        List<Partner> partners = userService.creteriaBasedOnAge(age);

        if (partners.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<PartnerResponseDTO> partnerResponseDTOS = PartnerConverter.convertToDTOList(partners);
        return ResponseEntity.ok(partnerResponseDTOS);
    }
}
