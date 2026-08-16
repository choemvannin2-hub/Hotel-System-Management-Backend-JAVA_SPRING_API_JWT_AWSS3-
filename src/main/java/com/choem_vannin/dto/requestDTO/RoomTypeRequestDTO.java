package com.choem_vannin.dto.requestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomTypeRequestDTO {
    @NotBlank(message = "roomType Name required.")
    @NotEmpty(message = "roomType Name required.")
    private String name;
    private String description;
    private Integer capacity;
    private BigDecimal pricePerNight;
}
