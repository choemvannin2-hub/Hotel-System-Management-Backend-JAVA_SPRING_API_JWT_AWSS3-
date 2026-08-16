package com.choem_vannin.service.interfaces;

import com.choem_vannin.dto.requestDTO.PaymentRequestDTO;
import com.choem_vannin.dto.responseDTO.PaymentResponseDTO;

public interface PaymentService {

    PaymentResponseDTO createPayment(PaymentRequestDTO requestDTO);
}
