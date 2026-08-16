package com.choem_vannin.service.impl;

import com.choem_vannin._security.JwtService;
import com.choem_vannin.dto.requestDTO.LoginRequestDTO;
import com.choem_vannin.dto.requestDTO.UserRequestDTO;
import com.choem_vannin.dto.responseDTO.LoginResponseDTO;
import com.choem_vannin.dto.responseDTO.UserResponseDTO;
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
    public UserResponseDTO register(UserRequestDTO requestDTO) {
        // TODO: 8/12/2026: Check Validation
        if ((requestDTO.getEmail() == null || requestDTO.getEmail().isBlank()) && (requestDTO.getPhone() == null || requestDTO.getPhone().isBlank())){
            throw new BadRequestException("Phone or Email required!");
        }
        if (requestDTO.getPhone() !=null && userRepository.findByPhone(requestDTO.getPhone()).isPresent()){
            throw new BadRequestException("This Phone number already existed.");
        }
        if (requestDTO.getEmail() !=null && userRepository.findByEmail(requestDTO.getEmail()).isPresent()){
            throw new BadRequestException("This Email already existed.");
        }

        // TODO: 8/12/2026: Set data into database
        // Convert data to Entity
        User user = UserMapper.toEntity(requestDTO);

        // Hash password
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        User savedUser = userRepository.save(user);

        return UserMapper.toResponse(savedUser);
    }

    // TODO: 8/12/2026: LOGIN ACCOUNT
    @Override
    public LoginResponseDTO login(LoginRequestDTO requestDTO) {
        // TODO: 8/12/2026: Check Validation
        if ((requestDTO.getEmail() == null || requestDTO.getEmail().isBlank()) && (requestDTO.getPhone() == null || requestDTO.getPhone().isBlank())){
            throw new BadRequestException("Phone or Email required!");
        }
        // find user
        User user = null;
        if (requestDTO.getEmail() != null){
            user = userRepository.findByEmail(requestDTO.getEmail()).orElseThrow(()->
                    new ResourceNotFoundException("Invalid email.")
                    );
        }else {
            user = userRepository.findByPhone(requestDTO.getPhone()).orElseThrow(()->
                    new ResourceNotFoundException("Invalid phone number.")
                    );
        }

        // Check password
        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())){    // -> matches( rawPassword, encryptPassword)
            throw new BadRequestException("Wrong password!");
        }

        // TODO: 8/12/2026: Generate token after validation check is corrected
        String token = jwtService.generateToken(user);

        return LoginResponseDTO.builder()
                .token(token)
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole().name())
                .build();
    }
}
