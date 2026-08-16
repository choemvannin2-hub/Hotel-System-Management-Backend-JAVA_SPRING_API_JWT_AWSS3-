package com.choem_vannin.dto.requestDTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequestDTO {
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Integer guest;
    private Long roomId;
}
