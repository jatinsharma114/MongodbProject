package com.test.mongodbTest.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartnerResponseDTO {
    private Integer id;
    private String name;
    private String details;
    private Integer age;
    private String updatedOn;
}
