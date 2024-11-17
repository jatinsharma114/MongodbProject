package com.test.mongodbTest.DTO;

import com.test.mongodbTest.Model.Partner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubDTO {
    
    private List<Partner> partners;
    private String otherField;
    private String createdOn;

}