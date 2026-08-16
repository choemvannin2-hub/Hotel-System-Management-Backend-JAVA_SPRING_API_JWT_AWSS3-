package com.choem_vannin.dto.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponseDTO {
    private Long id;
    private String roomNumber;
    private Integer floor;
    private String photoUrl;
    private RoomTypeResponseDTO roomType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
