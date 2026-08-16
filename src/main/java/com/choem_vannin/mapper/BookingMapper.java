package com.choem_vannin.mapper;

import com.choem_vannin.dto.requestDTO.BookingRequestDTO;
import com.choem_vannin.dto.responseDTO.BookingResponseDTO;
import com.choem_vannin.enums.PaymentStatus;
import com.choem_vannin.model.Booking;
import com.choem_vannin.model.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
@RequiredArgsConstructor
public class BookingMapper {


    public static Booking toEntity(BookingRequestDTO requestDTO){
        return Booking.builder()
                .checkIn(requestDTO.getCheckIn())
                .checkOut(requestDTO.getCheckOut())
                .guest(requestDTO.getGuest())
                .build();
    }

    public static BookingResponseDTO toResponse(Booking entity) {

        PaymentStatus latestPaymentStatus = null;

        if (entity.getPayments() != null && !entity.getPayments().isEmpty()) {
            latestPaymentStatus = entity.getPayments().stream()
                    // Ensure both paidAt and status are non-null
                    .filter(p -> p.getPaidAt() != null && p.getStatus() != null)
                    .max(Comparator.comparing(Payment::getPaidAt))
                    .map(p -> parsePaymentStatus(p.getStatus()))
                    .orElse(null);
        }

        return BookingResponseDTO.builder()
                .id(entity.getId())
                .bookingCode(entity.getBookingCode())
                .bookedBy(entity.getUser() != null ? entity.getUser().getEmail() : null)
                .checkIn(entity.getCheckIn())
                .checkOut(entity.getCheckOut())
                .guest(entity.getGuest())
                .roomNumber(entity.getRoom() != null ? entity.getRoom().getRoomNumber() : null)
                .totalPrice(entity.getTotalPrice())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .paymentStatus(latestPaymentStatus)
                .build();
    }

    // Helper method to keep stream clean and safe
    private static PaymentStatus parsePaymentStatus(Object status) {
        if (status == null) return null;
        try {
            // Works whether status is String or Enum
            return PaymentStatus.valueOf(status.toString().toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            return null; // Gracefully handle unmatched enum values
        }
    }
}

