package com.choem_vannin.dto.requestDTO;

import com.choem_vannin.enums.Amenity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class RoomTypeRequestDTO {
    @NotBlank(message = "roomType Name required.")
    private String name;
    private Set<Amenity> amenities;
}
