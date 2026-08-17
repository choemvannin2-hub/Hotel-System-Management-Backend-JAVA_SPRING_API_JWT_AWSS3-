package com.choem_vannin.service.interfaces;

import com.choem_vannin.dto.requestDTO.LoginRequestDTO;
import com.choem_vannin.dto.requestDTO.RegisterRequestDTO;
import com.choem_vannin.dto.responseDTO.LoginResponseDTO;
import com.choem_vannin.dto.responseDTO.ResgisterResponseDTO;

public interface AuthService {

    // TODO: 8/12/2026: Register account
    ResgisterResponseDTO register(RegisterRequestDTO requestDTO);
    // TODO: 8/12/2026: Login account
    LoginResponseDTO login(LoginRequestDTO requestDTO);
}
