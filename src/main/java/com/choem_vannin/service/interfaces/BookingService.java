package com.choem_vannin.service.interfaces;

import com.choem_vannin.dto.requestDTO.BookingRequestDTO;
import com.choem_vannin.dto.responseDTO.BookingResponseDTO;

import java.util.List;

public interface BookingService {
    BookingResponseDTO createBooking(BookingRequestDTO requestDTO);
    BookingResponseDTO getById(Long id);
    List<BookingResponseDTO> getAll();
    BookingResponseDTO confirmBooking(Long id);
    BookingResponseDTO cancelBooking(Long id);
    BookingResponseDTO checkIn(Long id);
    BookingResponseDTO checkOut(Long id);
}
