package com.test.mongodbTest.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Data
@AllArgsConstructor
@Document(collection = "C1")
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Club {
    
    private List<Partner> partners;
    private String otherField;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String createdOn;

}
/**
 * @JsonIgnoreProperties(ignoreUnknown = true)
 * Even if some field are not present while sending in the PAYLOAD It will ignore while Deserrialization in JAVA class
 *
 * - So If only location field is not present in the class it will be ignore!
 * - Thanks to this!!
 *
 * IF not then If @JsonIgnoreProperties(ignoreUnknown = true) is not used,
 * Jackson will throw an UnrecognizedPropertyException.
 *
 * // Kailash Kher <3
 * // Branch 114
 *
 * // NEW LINE commit !
 */