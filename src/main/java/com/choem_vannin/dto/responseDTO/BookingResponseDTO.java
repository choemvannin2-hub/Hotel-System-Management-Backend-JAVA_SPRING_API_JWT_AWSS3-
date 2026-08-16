package com.choem_vannin.dto.responseDTO;

import com.choem_vannin.enums.BookingStatus;
import com.choem_vannin.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDTO {
    private Long id;
    private String bookingCode;
    private String bookedBy;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Integer guest;
    private String roomNumber;
    private BigDecimal totalPrice;
    private BookingStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private PaymentStatus paymentStatus;
}
