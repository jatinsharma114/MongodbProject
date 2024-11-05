package com.test.mongodbTest.Controller;


import com.test.mongodbTest.DTO.PartnerResponse;
import com.test.mongodbTest.Service.PDFUpdatePartner;
import com.test.mongodbTest.Repository.MongoDBRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@RestController
public class PDFReaderController {
    @Autowired
    private PDFUpdatePartner pdfUpdatePartner;

    @PostMapping("/uploadpdf")
    public ResponseEntity<List<PartnerResponse>> updatePartnersFromCsv(@RequestParam("file") MultipartFile file) {
        try {
            List<PartnerResponse> responses = pdfUpdatePartner.updatePartnersFromUploadedCsv(file);
            return ResponseEntity.ok(responses);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
