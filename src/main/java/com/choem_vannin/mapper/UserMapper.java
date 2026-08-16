package com.choem_vannin.mapper;


import com.choem_vannin.dto.requestDTO.UserRequestDTO;
import com.choem_vannin.dto.responseDTO.UserResponseDTO;
import com.choem_vannin.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // TODO: 8/12/2026: Convert data to Entity type(User type)
    public static User toEntity(UserRequestDTO requestDTO){
        if (requestDTO == null) return null;

        return User.builder()
                .firstName(requestDTO.getFirst_name())
                .lastName(requestDTO.getLast_name())
                .email(requestDTO.getEmail())
                .phone(requestDTO.getPhone())
                .password(requestDTO.getPassword())
                .build();
    }

    // TODO: 8/12/2026: Convert data from Entity to response Type
    public static UserResponseDTO toResponse(User entity){
        if (entity == null) return null;

        return UserResponseDTO.builder()
                .id(entity.getId())
                .first_name(entity.getFirstName())
                .last_name(entity.getLastName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .role(entity.getRole())
                .bookings(entity.getBookings())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
