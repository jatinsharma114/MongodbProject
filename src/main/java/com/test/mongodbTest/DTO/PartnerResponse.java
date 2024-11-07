package com.test.mongodbTest.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields from JSON response
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PartnerResponse {
    private Integer id;
    private String name;
    private String details;
    private Integer age;
    private String updatedOn;
}
