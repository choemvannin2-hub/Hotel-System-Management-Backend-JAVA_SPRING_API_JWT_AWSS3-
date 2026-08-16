package com.choem_vannin.dto.responseDTO;

import com.choem_vannin.enums.PaymentMethod;
import com.choem_vannin.enums.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentResponseDTO {

    private Long id;

    private String paymentCode;

    private BigDecimal amount;

    private PaymentStatus status;

    private PaymentMethod paymentMethod;

    private String qrData;

    private LocalDateTime paidAt;

    private LocalDateTime createdAt;

    private Long bookingId;
}
