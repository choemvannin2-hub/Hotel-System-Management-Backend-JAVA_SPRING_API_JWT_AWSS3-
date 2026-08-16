package com.choem_vannin.repository;

import com.choem_vannin.dto.responseDTO.BookingResponseDTO;
import com.choem_vannin.enums.BookingStatus;
import com.choem_vannin.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByRoomIdAndCheckInLessThanAndCheckOutGreaterThanAndStatusIn(
            Long roomId,
            LocalDate checkOut,
            LocalDate checkIn,
            List<BookingStatus> statuses
    );

    boolean existsByBookingCode(String bookingCode);
}
