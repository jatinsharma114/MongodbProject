package com.test.mongodbTest.Controller;

import com.test.mongodbTest.Model.Partner;
import com.test.mongodbTest.Model.PartnerContainer;
import com.test.mongodbTest.Service.ExportPartnersToSaveOnLocalDisk;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ExportPartnersInExcelSheet {
    @Autowired
    private ExportPartnersToSaveOnLocalDisk exportService;

    @PostMapping("/exportPartners")
    public ResponseEntity<Void> exportPartners() {
        String filePath = "E:\\Coding Work\\partners1.xlsx"; // Define the file path
        try {
            exportService.exportPartnersToExcel(filePath);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            // Handle the exception (e.g., log it and return an error response)
            return ResponseEntity.status(500).build(); // Internal server error
        }
    }
}
