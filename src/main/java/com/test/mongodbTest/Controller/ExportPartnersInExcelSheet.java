package com.test.mongodbTest.Controller;

import com.test.mongodbTest.Service.ExportPartnersToSaveOnLocalDisk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@RestController
public class ExportPartnersInExcelSheet {
    @Autowired
    private ExportPartnersToSaveOnLocalDisk exportService;

    @Value("${export.partners.filePath}")
    private String filePath;

    @PostMapping("/exportPartners")
    public ResponseEntity<Void> exportPartners() {
        try {
            exportService.exportPartnersToExcel(filePath);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            // Handle the exception (e.g., log it and return an error response)
            return ResponseEntity.status(500).build(); // Internal server error
        }
    }
}
