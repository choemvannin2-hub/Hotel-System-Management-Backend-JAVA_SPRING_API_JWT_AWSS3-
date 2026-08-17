package com.choem_vannin.mapper;

import com.choem_vannin.dto.requestDTO.RoomTypeRequestDTO;
import com.choem_vannin.dto.responseDTO.RoomResponseDTO;
import com.choem_vannin.dto.responseDTO.RoomTypeResponseDTO;
import com.choem_vannin.model.RoomType;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class RoomTypeMapper {

    public static RoomType toEntity(RoomTypeRequestDTO requestDTO){
        if (requestDTO == null) return null;

        return RoomType.builder()
                .name(requestDTO.getName())
                .amenities(requestDTO.getAmenities() != null ?  // prevent null
                        new HashSet<>(requestDTO.getAmenities()) : new HashSet<>()
                )
                .build();
    }

    public static RoomTypeResponseDTO toResponse(RoomType entity){
        if (entity == null) return null;

        return RoomTypeResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .amenities(entity.getAmenities())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
