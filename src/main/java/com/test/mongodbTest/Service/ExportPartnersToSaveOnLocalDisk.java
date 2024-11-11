package com.test.mongodbTest.Service;

import com.test.mongodbTest.Model.Partner;
import com.test.mongodbTest.Repository.PartnerContainerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import com.test.mongodbTest.Model.PartnerContainer;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExportPartnersToSaveOnLocalDisk {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PartnerContainerRepo partnerContainerRepo;

    public List<Partner> getAllPartners() {

        List<PartnerContainer> containers = partnerContainerRepo.findAll();

        // Collect partners from all containers
        return containers.stream()
                .flatMap(container -> container.getPartners().stream())
                .collect(Collectors.toList());
    }

    public void exportPartnersToExcel(String filePath) throws IOException {
        List<Partner> partners = getAllPartners(); // Fetch all partners

        // Create an Excel workbook and sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Partners");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("Details");
        headerRow.createCell(3).setCellValue("Age");
        headerRow.createCell(4).setCellValue("Updated On");

        // Populate rows with Partner data
        int rowNum = 1;
        for (Partner partner : partners) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(partner.getId());
            row.createCell(1).setCellValue(partner.getName());
            row.createCell(2).setCellValue(partner.getDetails());
            row.createCell(3).setCellValue(partner.getAge());
            row.createCell(4).setCellValue(partner.getUpdatedOn());
        }

        // Save the Excel file to the specified location
        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
            System.out.println("File saved to " + filePath);
        } finally {
            workbook.close();
        }
    }
}
