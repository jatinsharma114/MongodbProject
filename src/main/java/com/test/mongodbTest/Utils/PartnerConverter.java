package com.test.mongodbTest.Utils;

import com.test.mongodbTest.DTO.PartnerDTO;
import com.test.mongodbTest.Model.Partner;

import java.util.List;
import java.util.stream.Collectors;

public class PartnerConverter {
    public static PartnerDTO convertToDTO(Partner partner) {
        if (partner == null) {
            return null;
        }
        
        PartnerDTO dto = new PartnerDTO();
        dto.setId(partner.getId());
        dto.setName(partner.getName());
        dto.setDetails(partner.getDetails());
        dto.setAge(partner.getAge());
        dto.setUpdatedOn(partner.getUpdatedOn());
        return dto;
    }

    public static List<PartnerDTO> convertToDTOList(List<Partner> partners) {
        return partners.stream()
                       .map(PartnerConverter::convertToDTO)
                       .collect(Collectors.toList());
    }
}
