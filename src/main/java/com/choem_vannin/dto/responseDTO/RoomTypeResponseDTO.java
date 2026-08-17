package com.choem_vannin.dto.responseDTO;

import com.choem_vannin.enums.Amenity;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class RoomTypeResponseDTO {
    private Long id;
    private String name;
    private Set<Amenity> amenities;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
