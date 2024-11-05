package com.test.mongodbTest.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.test.mongodbTest.Model.Partner;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Data
@NoArgsConstructor
@Document(collection = "C1")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PartnerContainer {
    
    private List<Partner> partners;
    private String otherField;
    private String createdOn;

}