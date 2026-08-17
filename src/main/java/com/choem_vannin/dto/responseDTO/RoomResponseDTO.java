package com.choem_vannin.dto.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponseDTO {
    private Long id;
    private String roomNumber;
    private Integer floor;
    private String description;
    private Integer capacity;
    private BigDecimal pricePerNight;
    private String photoUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long roomType;
    private String roomTypeName;
}
