package com.test.mongodbTest.Service;

import com.test.mongodbTest.DTO.PartnerResponse;
import com.test.mongodbTest.Model.Partner;
import com.test.mongodbTest.Utils.GlobalConstant;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class PDFUpdatePartner {
    @Autowired
    PartnerContainerRepositoryCustom partnerContainerRepositoryCustom;

    public List<PartnerResponse> updatePartnersFromUploadedCsv(MultipartFile file) throws IOException {
        List<PartnerResponse> updatedPartnerResponses = new ArrayList<>();

        // Create Workbook instance
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();

        // Just to --> Skip header row!!
        boolean isHeader = true;

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (isHeader) {
                isHeader = false; // Skip header row
                continue;
            }

            // Read and parse each cell, handling both numeric and string types
            Integer partnerId = getIntegerCellValue(row.getCell(0)); // ID
            String name = getStringCellValue(row.getCell(1)); // Name
            String details = getStringCellValue(row.getCell(2)); // Details
            Integer age = getIntegerCellValue(row.getCell(3)); // Age

            // Update the Single Partner with Partner ID in DB
            Partner updatedPartner = Partner.builder()
                    .id(partnerId)
                    .name(name)
                    .age(age)
                    .details(details)
                    .updatedOn(LocalDateTime.now().format(GlobalConstant.FORMATTER))
                    .build();

            // Assuming you have a method in your repository to update the partner
            partnerContainerRepositoryCustom.updatePartner(partnerId, updatedPartner);

            // Create PartnerResponse using Lombok builder
            PartnerResponse response = PartnerResponse.builder()
                    .id(updatedPartner.getId())
                    .name(updatedPartner.getName())
                    .details(updatedPartner.getDetails())
                    .age(updatedPartner.getAge())
                    .updatedOn(updatedPartner.getUpdatedOn())
                    .build();

            // Add the response to the list
            updatedPartnerResponses.add(response);
        }

        workbook.close();

        // Return the response list
        return updatedPartnerResponses;
    }

    private Integer getIntegerCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }

        if (cell.getCellType() == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            try {
                return Integer.parseInt(cell.getStringCellValue().trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid numeric value in cell: " + cell.getStringCellValue());
            }
        }
        return null;
    }

    private String getStringCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }

        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf((int) cell.getNumericCellValue());
        }
        return null;
    }
}
