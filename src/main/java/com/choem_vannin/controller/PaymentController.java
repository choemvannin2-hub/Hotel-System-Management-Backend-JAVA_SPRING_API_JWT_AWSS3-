package com.choem_vannin.controller;

import com.choem_vannin.dto.requestDTO.PaymentRequestDTO;
import com.choem_vannin.dto.responseDTO.PaymentResponseDTO;
import com.choem_vannin.service.interfaces.PaymentService;
import com.choem_vannin.utils.ApiResponse;
import com.choem_vannin.utils.ApiResponseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ApiResponseHelper<PaymentResponseDTO>> createPayment(@RequestBody PaymentRequestDTO requestDTO){
        PaymentResponseDTO responseDTO = paymentService.createPayment(requestDTO);
        return ApiResponse.ok(responseDTO, "Booking id:"+requestDTO.getBookingId()+" paid.");
    }
}
