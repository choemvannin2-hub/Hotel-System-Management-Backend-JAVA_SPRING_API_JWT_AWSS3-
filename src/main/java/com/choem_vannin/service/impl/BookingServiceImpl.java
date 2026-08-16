package com.choem_vannin.service.impl;

import com.choem_vannin.dto.requestDTO.BookingRequestDTO;
import com.choem_vannin.dto.responseDTO.BookingResponseDTO;
import com.choem_vannin.enums.BookingStatus;
import com.choem_vannin.enums.PaymentStatus;
import com.choem_vannin.execption.BadRequestException;
import com.choem_vannin.execption.DuplicateSkuException;
import com.choem_vannin.execption.ForbiddenException;
import com.choem_vannin.execption.ResourceNotFoundException;
import com.choem_vannin.mapper.BookingMapper;
import com.choem_vannin.model.Booking;
import com.choem_vannin.model.Room;
import com.choem_vannin.model.User;
import com.choem_vannin.repository.BookingRepository;
import com.choem_vannin.repository.RoomRepository;
import com.choem_vannin.repository.UserRepository;
import com.choem_vannin.service.interfaces.BookingService;
import com.choem_vannin.utils.BookingCodeGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    
    @Override
    @Transactional
    public BookingResponseDTO createBooking(BookingRequestDTO requestDTO) {
        // Booking date validation
        if (!requestDTO.getCheckIn().isBefore(requestDTO.getCheckOut())) {
            throw new BadRequestException("Invalid booking dates");
        }

        // Find room
        Room room = roomRepository.findById(requestDTO.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        // Check availability by idRoomAvailable method below
        boolean available = isRoomAvailable(room.getId(), requestDTO.getCheckIn(), requestDTO.getCheckOut());
        if (!available) {
            throw new DuplicateSkuException("Room is not available for these dates");
        }

        // Calculate price
        BigDecimal pricePerNight = room.getRoomType().getPricePerNight(); // find price per night of the room
        BigDecimal night = BigDecimal.valueOf(ChronoUnit.DAYS.between(requestDTO.getCheckIn(), requestDTO.getCheckOut()));// find night by date
        BigDecimal totalPrice = pricePerNight.multiply(night); // calculate totalPrice

        // Get authenticated user
        User user = getUserAuth(); // get by method below

        // Generate booking code
        String bookingCode;
        do {
            bookingCode = BookingCodeGenerator.generateCode();
        }while (bookingRepository.existsByBookingCode(bookingCode)); // check to avoid duplicate code

        // Save booking
        Booking booking = BookingMapper.toEntity(requestDTO);
        booking.setBookingCode(bookingCode);
        booking.setStatus(BookingStatus.PENDING); // Set PENDING
        booking.setTotalPrice(totalPrice);
        booking.setUser(user); // User who log in
        booking.setRoom(room);

        Booking savedBooking = bookingRepository.save(booking);
        return BookingMapper.toResponse(savedBooking);
    }
    // TODO: 8/14/2026: Check available room method
    public boolean isRoomAvailable(
            Long roomId,
            LocalDate checkIn,
            LocalDate checkOut
    ) {

        List<BookingStatus> blockingStatuses = List.of(
                BookingStatus.PENDING,
                BookingStatus.CONFIRMED,
                BookingStatus.CHECKED_IN
        );

        return !bookingRepository
                .existsByRoomIdAndCheckInLessThanAndCheckOutGreaterThanAndStatusIn(
                        roomId,
                        checkOut,
                        checkIn,
                        blockingStatuses
                );
    }
    // TODO: 8/14/2026: Get User Authentication method
    public User getUserAuth(){
        // Get Authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ForbiddenException("User is not authenticated");
        }

        // Extract username / email
        String email = authentication.getName(); // or (UserDetails) authentication.getPrincipal()

        // Fetch User entity from Database
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found"));
    }



    @Override
    public BookingResponseDTO getById(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("This booking not found.")
        );
        return BookingMapper.toResponse(booking);
    }

    @Override
    public List<BookingResponseDTO> getAll() {
        return bookingRepository.findAll().stream().map(BookingMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public BookingResponseDTO confirmBooking(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() ->
                        new ResourceNotFoundException("This booking was not found.")
                );

        // Booking must be PENDING
        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new BadRequestException("Only pending bookings can be confirmed.");
        }

        // TODO: 8/15/2026: This is just fake payment, the real payment will be impl next time
        boolean hasPaidPayment = booking.getPayments().stream().anyMatch(payment ->
                        payment.getStatus() == PaymentStatus.PAID
                );

        if (!hasPaidPayment) {
            throw new BadRequestException("Booking cannot be confirmed because payment has not been completed.");
        }

        // Confirm booking
        booking.setStatus(BookingStatus.CONFIRMED);

        Booking savedBooking = bookingRepository.save(booking);
        return BookingMapper.toResponse(savedBooking);
    }

    @Override
    @Transactional
    public BookingResponseDTO cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found with id: " + id)
                );

        // Cannot cancel an already completed or cancelled booking
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new RuntimeException("Booking is already cancelled.");
        }

        if (booking.getStatus() == BookingStatus.CHECKED_OUT) {
            throw new RuntimeException("Cannot cancel a completed booking.");
        }

        if (booking.getStatus() == BookingStatus.CHECKED_IN) {
            throw new RuntimeException("Cannot cancel a checked-in booking.");
        }

        booking.setStatus(BookingStatus.CANCELLED);

        Booking savedBooking = bookingRepository.save(booking);

        return BookingMapper.toResponse(savedBooking);
    }

    @Override
    @Transactional
    public BookingResponseDTO checkIn(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found with id: " + id)
                );

        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new RuntimeException(
                    "Only confirmed bookings can be checked in."
            );
        }

        LocalDate today = LocalDate.now();

        if (today.isBefore(booking.getCheckIn())) {
            throw new RuntimeException(
                    "Check-in date has not arrived yet."
            );
        }

        if (!today.isBefore(booking.getCheckOut())) {
            throw new RuntimeException(
                    "Booking has already reached the checkout date."
            );
        }

        booking.setStatus(BookingStatus.CHECKED_IN);

        Booking savedBooking = bookingRepository.save(booking);

        return BookingMapper.toResponse(savedBooking);
    }

    @Override
    @Transactional
    public BookingResponseDTO checkOut(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found with id: " + id)
                );

        if (booking.getStatus() != BookingStatus.CHECKED_IN) {
            throw new RuntimeException(
                    "Only checked-in bookings can be checked out."
            );
        }

        booking.setStatus(BookingStatus.CHECKED_OUT);
        Booking savedBooking = bookingRepository.save(booking);

        return BookingMapper.toResponse(savedBooking);
    }
}
