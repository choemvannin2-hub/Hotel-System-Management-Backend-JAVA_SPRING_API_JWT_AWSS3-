package com.choem_vannin.dto.responseDTO;

import com.choem_vannin.enums.Roles;
import com.choem_vannin.model.Booking;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResgisterResponseDTO {
    private Long id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Roles role;

    private List<Booking> bookings;
}
