package com.choem_vannin.service.impl;

import com.choem_vannin._security.JwtService;
import com.choem_vannin.dto.requestDTO.LoginRequestDTO;
import com.choem_vannin.dto.requestDTO.RegisterRequestDTO;
import com.choem_vannin.dto.responseDTO.LoginResponseDTO;
import com.choem_vannin.dto.responseDTO.ResgisterResponseDTO;
import com.choem_vannin.execption.BadRequestException;
import com.choem_vannin.execption.ResourceNotFoundException;
import com.choem_vannin.mapper.UserMapper;
import com.choem_vannin.model.User;
import com.choem_vannin.repository.UserRepository;
import com.choem_vannin.service.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // TODO: 8/12/2026: REGISTER ACCOUNT
    @Override
    public ResgisterResponseDTO register(RegisterRequestDTO requestDTO) {
        // TODO: 8/12/2026: Check Validation
        // Validate email
        if (requestDTO.getEmail() == null || requestDTO.getEmail().isBlank()) {
            throw new BadRequestException("Email is required.");
        }

        // Check duplicate email
        if (userRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
            throw new BadRequestException("This Email already exists.");
        }

        // Check duplicate phone only if provided
        if (requestDTO.getPhone() != null && !requestDTO.getPhone().isBlank() &&
                userRepository.findByPhone(requestDTO.getPhone()).isPresent()) {

            throw new BadRequestException("This Phone number already exists.");
        }

        // TODO: 8/12/2026: Set data into database
        // Convert DTO → Entity
        User user = UserMapper.toEntity(requestDTO);

        // Hash password
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        // Save
        User savedUser = userRepository.save(user);

        return UserMapper.toResponse(savedUser);
    }

    // TODO: 8/12/2026: LOGIN ACCOUNT
    @Override
    public LoginResponseDTO login(LoginRequestDTO requestDTO) {
        // Validate identifier
        if (requestDTO.getIdentifier() == null || requestDTO.getIdentifier().isBlank()) {
            throw new BadRequestException("Email or phone is required.");
        }

        // Find user by email OR phone
        User user = userRepository.findByEmail(requestDTO.getIdentifier()).orElseGet(() ->
                        userRepository.findByPhone(requestDTO.getIdentifier()).orElseThrow(() ->

                                new ResourceNotFoundException("Invalid email or phone number.")
                        )
        );

        // Check password
        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
            throw new BadRequestException("Wrong password!");
        }

        // Generate JWT
        String token = jwtService.generateToken(user);

        return LoginResponseDTO.builder()
                .token(token)
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole().name())
                .build();
    }
}
