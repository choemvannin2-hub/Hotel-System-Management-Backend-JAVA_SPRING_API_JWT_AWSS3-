package com.choem_vannin.dto.requestDTO;

import com.choem_vannin.model.RoomType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomRequestDTO {
    private String roomNumber;
    private Integer floor;
    private String description;
    private Integer capacity;
    private BigDecimal pricePerNight;
    private String photoUrl;
    private Long roomTypeId;
}
