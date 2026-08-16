package com.choem_vannin.controller;

import com.choem_vannin.dto.requestDTO.BookingRequestDTO;
import com.choem_vannin.dto.responseDTO.BookingResponseDTO;
import com.choem_vannin.service.interfaces.BookingService;
import com.choem_vannin.utils.ApiResponse;
import com.choem_vannin.utils.ApiResponseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseHelper<BookingResponseDTO>> create(@RequestBody BookingRequestDTO requestDTO){
        BookingResponseDTO responseDTO =bookingService.createBooking(requestDTO);
        return ApiResponse.ok(responseDTO, "Booked successfully");
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER','STAFF')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponseHelper<List<BookingResponseDTO>>> getAll(){
        List<BookingResponseDTO> responseDTOS = bookingService.getAll();
        return ApiResponse.ok(responseDTOS, "Get All booked successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseHelper<BookingResponseDTO>> getById(@PathVariable Long id){
        BookingResponseDTO responseDTO = bookingService.getById(id);
        return ApiResponse.ok(responseDTO, "Get Booked by id: "+id+" successfully.");
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER','STAFF')")
    @PutMapping("/{id}/confirm")
    public ResponseEntity<ApiResponseHelper<BookingResponseDTO>> confirmBooking(@PathVariable Long id){
        BookingResponseDTO responseDTO = bookingService.confirmBooking(id);
        return ApiResponse.ok(responseDTO, "Booking Confirmed.");
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER','STAFF')")
    @PutMapping("/{id}/cancel")
    public ResponseEntity<ApiResponseHelper<BookingResponseDTO>> cancelBooking(@PathVariable Long id){
        BookingResponseDTO responseDTO = bookingService.cancelBooking(id);
        return ApiResponse.ok(responseDTO, "Booking Cancelled.");
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER','STAFF')")
    @PutMapping("/{id}/check-in")
    public ResponseEntity<ApiResponseHelper<BookingResponseDTO>> checkIn(@PathVariable Long id){
        BookingResponseDTO responseDTO = bookingService.checkIn(id);
        return ApiResponse.ok(responseDTO, "Booking Cancelled.");
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER','STAFF')")
    @PutMapping("/{id}/check-out")
    public ResponseEntity<ApiResponseHelper<BookingResponseDTO>> checkOut(@PathVariable Long id){
        BookingResponseDTO responseDTO = bookingService.checkOut(id);
        return ApiResponse.ok(responseDTO, "Booking Cancelled.");
    }

}
