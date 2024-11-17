package com.test.mongodbTest.Utils;

import com.test.mongodbTest.DTO.PartnerResponseDTO;
import com.test.mongodbTest.Model.Partner;

import java.util.List;
import java.util.stream.Collectors;

public class PartnerConverter {
    public static PartnerResponseDTO convertToDTO(Partner partner) {
        if (partner == null) {
            return null;
        }
        
        PartnerResponseDTO dto = new PartnerResponseDTO();
        dto.setId(partner.getId());
        dto.setName(partner.getName());
        dto.setDetails(partner.getDetails());
        dto.setAge(partner.getAge());
        dto.setUpdatedOn(partner.getUpdatedOn());
        return dto;
    }

    public static List<PartnerResponseDTO> convertToDTOList(List<Partner> partners) {
        return partners.stream()
                       .map(PartnerConverter::convertToDTO)
                       .collect(Collectors.toList());
    }
}
