package com.choem_vannin.mapper;


import com.choem_vannin.dto.responseDTO.PaymentResponseDTO;
import com.choem_vannin.model.Payment;

public class PaymentMapper {

    public static PaymentResponseDTO toResponseDTO(Payment payment) {

        PaymentResponseDTO dto = new PaymentResponseDTO();

        dto.setId(payment.getId());
        dto.setPaymentCode(payment.getPaymentCode());
        dto.setAmount(payment.getAmount());
        dto.setStatus(payment.getStatus());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setQrData(payment.getQrData());
        dto.setPaidAt(payment.getPaidAt());
        dto.setCreatedAt(payment.getCreatedAt());

        if (payment.getBooking() != null) {
            dto.setBookingId(payment.getBooking().getId());
        }

        return dto;
    }
}
