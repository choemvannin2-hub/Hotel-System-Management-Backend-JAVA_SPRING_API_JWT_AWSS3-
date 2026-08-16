package com.choem_vannin.dto.responseDTO;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class RoomTypeResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Integer capacity;
    private BigDecimal pricePerNight;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
