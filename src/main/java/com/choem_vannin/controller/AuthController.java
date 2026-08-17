package com.choem_vannin.controller;

import com.choem_vannin.dto.requestDTO.LoginRequestDTO;
import com.choem_vannin.dto.requestDTO.RegisterRequestDTO;
import com.choem_vannin.dto.responseDTO.LoginResponseDTO;
import com.choem_vannin.dto.responseDTO.ResgisterResponseDTO;
import com.choem_vannin.service.interfaces.AuthService;
import com.choem_vannin.utils.ApiResponse;
import com.choem_vannin.utils.ApiResponseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseHelper<ResgisterResponseDTO>> register(@RequestBody RegisterRequestDTO requestDTO){
        ResgisterResponseDTO responseDTO = authService.register(requestDTO);
        return ApiResponse.ok(responseDTO, "Created account successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseHelper<LoginResponseDTO>> login(@RequestBody LoginRequestDTO requestDTO){
        LoginResponseDTO responseDTO = authService.login(requestDTO);
        return ApiResponse.ok(responseDTO, "Login successfully.");
    }

}
