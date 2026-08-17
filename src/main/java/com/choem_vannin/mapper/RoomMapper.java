package com.choem_vannin.mapper;

import com.choem_vannin.dto.requestDTO.RoomRequestDTO;
import com.choem_vannin.dto.responseDTO.RoomResponseDTO;
import com.choem_vannin.model.Room;
import com.choem_vannin.model.RoomType;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {
    public static Room toEntity(RoomRequestDTO requestDTO){
        if (requestDTO == null) return null;

        RoomType roomType = null;
        if (requestDTO.getRoomTypeId() != null) {
            roomType = RoomType.builder()
                    .id(requestDTO.getRoomTypeId())
                    .build();
        }

        return Room.builder()
                .roomNumber(requestDTO.getRoomNumber())
                .floor(requestDTO.getFloor())
                .description(requestDTO.getDescription())
                .capacity(requestDTO.getCapacity())
                .pricePerNight(requestDTO.getPricePerNight())
                .photoUrl(requestDTO.getPhotoUrl())
                .roomType(roomType)
                .build();
    }

    public static RoomResponseDTO toResponse(Room entity){
        return RoomResponseDTO.builder()
                .id(entity.getId())
                .roomNumber(entity.getRoomNumber())
                .floor(entity.getFloor())
                .description(entity.getDescription())
                .capacity(entity.getCapacity())
                .pricePerNight(entity.getPricePerNight())
                .photoUrl(entity.getPhotoUrl())
                .roomType(entity.getRoomType().getId())
                .roomTypeName(entity.getRoomType().getName())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
