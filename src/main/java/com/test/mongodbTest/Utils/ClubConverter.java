package com.test.mongodbTest.Utils;

import com.test.mongodbTest.DTO.ClubDTO;
import com.test.mongodbTest.Model.Club;

import java.util.List;
import java.util.stream.Collectors;

public class ClubConverter {
    public static ClubDTO convertToDTO(Club club) {
        if (club == null) {
            return null;
        }

        ClubDTO dto = new ClubDTO();
        dto.setOtherField(club.getOtherField()); // Assuming Club has a method getOtherField()
        dto.setCreatedOn(club.getCreatedOn()); // Assuming Club has a method getCreatedOn()
        dto.setPartners(club.getPartners()); // Assuming Club has a method getPartners()
        return dto;
    }

    public static List<ClubDTO> convertToDTOList(List<Club> clubs) {
        return clubs.stream()
                    .map(ClubConverter::convertToDTO)
                    .collect(Collectors.toList());
    }
}
