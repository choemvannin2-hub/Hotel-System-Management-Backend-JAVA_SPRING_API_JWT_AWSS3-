package com.choem_vannin.dto.requestDTO;

import lombok.Data;

@Data
public class LoginRequestDTO {
    // TODO: 8/11/2026: Every request must be check whether email or phone is existed.
    private String email;
    private String phone;
    private String password;
}
