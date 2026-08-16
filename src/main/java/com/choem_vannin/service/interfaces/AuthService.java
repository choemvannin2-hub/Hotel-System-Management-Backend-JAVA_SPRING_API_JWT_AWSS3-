package com.choem_vannin.service.interfaces;

import com.choem_vannin.dto.requestDTO.LoginRequestDTO;
import com.choem_vannin.dto.requestDTO.UserRequestDTO;
import com.choem_vannin.dto.responseDTO.LoginResponseDTO;
import com.choem_vannin.dto.responseDTO.UserResponseDTO;

public interface AuthService {

    // TODO: 8/12/2026: Register account
    UserResponseDTO register(UserRequestDTO requestDTO);
    // TODO: 8/12/2026: Login account
    LoginResponseDTO login(LoginRequestDTO requestDTO);
}
