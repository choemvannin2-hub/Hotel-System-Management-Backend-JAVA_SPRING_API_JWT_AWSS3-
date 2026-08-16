package com.choem_vannin.dto.requestDTO;

import com.choem_vannin.model.RoomType;
import lombok.Data;

@Data
public class RoomRequestDTO {
    private String roomNumber;
    private Integer floor;
    private String photoUrl;
    private Long roomTypeId;
}
