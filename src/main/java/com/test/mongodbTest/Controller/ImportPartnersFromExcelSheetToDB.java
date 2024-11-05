package com.test.mongodbTest.Controller;


import com.test.mongodbTest.DTO.PartnerResponse;
import com.test.mongodbTest.Service.ImportPartnersToSaveInDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@RestController
public class ImportPartnersFromExcelSheetToDB {
    @Autowired
    private ImportPartnersToSaveInDB pdfUpdatePartner;

    @PostMapping("/uploadpdf")
    public ResponseEntity<List<PartnerResponse>> updatePartnersFromCsv(@RequestParam("file") MultipartFile file) {
        try {
            List<PartnerResponse> responses = pdfUpdatePartner.importPartnersAndSaveInDB(file);
            return ResponseEntity.ok(responses);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }



}
