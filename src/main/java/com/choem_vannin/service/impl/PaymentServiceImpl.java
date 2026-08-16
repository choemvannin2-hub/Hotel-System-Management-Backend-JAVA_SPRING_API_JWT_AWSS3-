package com.choem_vannin.service.impl;

import com.choem_vannin.dto.requestDTO.PaymentRequestDTO;
import com.choem_vannin.dto.responseDTO.PaymentResponseDTO;
import com.choem_vannin.enums.BookingStatus;
import com.choem_vannin.enums.PaymentMethod;
import com.choem_vannin.enums.PaymentStatus;
import com.choem_vannin.mapper.PaymentMapper;
import com.choem_vannin.model.Booking;
import com.choem_vannin.model.Payment;
import com.choem_vannin.repository.BookingRepository;
import com.choem_vannin.repository.PaymentRepository;
import com.choem_vannin.service.interfaces.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    @Override
    @Transactional
    public PaymentResponseDTO createPayment(PaymentRequestDTO request) {

        // Find booking
        Booking booking = bookingRepository.findById(request.getBookingId()).orElseThrow(() ->
                        new RuntimeException("Booking not found")
                );

        // Check booking status
        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new RuntimeException("Payment can only be created for a pending booking");
        }

        // Check if booking already has a payment
        if (paymentRepository.findByBookingId(booking.getId()).isPresent()) {
            throw new RuntimeException("This booking already has a payment");
        }

        // Create payment
        Payment payment = new Payment();

        // Generate payment code
        String paymentCode = "PAY-" + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "-" +
                        UUID.randomUUID()
                                .toString()
                                .substring(0, 6)
                                .toUpperCase();
        payment.setPaymentCode(paymentCode);

        // Get amount from booking
        payment.setAmount(booking.getTotalPrice());

        // Initial payment status
        payment.setStatus(PaymentStatus.PAID);

        // Payment method
        payment.setPaymentMethod(PaymentMethod.KHQR);

        payment.setPaidAt(LocalDateTime.now());

        // Connect payment with booking
        payment.setBooking(booking);

        // Save payment
        Payment savedPayment = paymentRepository.save(payment);

        return PaymentMapper.toResponseDTO(savedPayment);
    }

}
