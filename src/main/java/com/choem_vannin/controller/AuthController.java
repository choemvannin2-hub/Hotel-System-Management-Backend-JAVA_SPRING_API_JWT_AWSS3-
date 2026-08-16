package com.choem_vannin.controller;

import com.choem_vannin._security.JwtService;
import com.choem_vannin.dto.requestDTO.LoginRequestDTO;
import com.choem_vannin.dto.requestDTO.UserRequestDTO;
import com.choem_vannin.dto.responseDTO.LoginResponseDTO;
import com.choem_vannin.dto.responseDTO.UserResponseDTO;
import com.choem_vannin.model.User;
import com.choem_vannin.service.interfaces.AuthService;
import com.choem_vannin.utils.ApiResponse;
import com.choem_vannin.utils.ApiResponseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseHelper<UserResponseDTO>> register(@RequestBody UserRequestDTO requestDTO){
        UserResponseDTO responseDTO = authService.register(requestDTO);
        return ApiResponse.ok(responseDTO, "Created account successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseHelper<LoginResponseDTO>> login(@RequestBody LoginRequestDTO requestDTO){
        LoginResponseDTO responseDTO = authService.login(requestDTO);
        return ApiResponse.ok(responseDTO, "Login successfully.");
    }

}
