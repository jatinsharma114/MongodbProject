package com.test.mongodbTest.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields from JSON response
@Data // Generates getters and setters
@Builder // Provides builder pattern
@NoArgsConstructor // Generates a no-args constructor
@AllArgsConstructor // Generates a constructor with parameters
public class PartnerResponse {
    private Integer id;
    private String name;
    private String details;
    private Integer age;
    private String updatedOn;
}
