package com.choem_vannin.dto.requestDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDTO {
    private String first_name;
    private String last_name;

    @Email(message = "Email format invalid.")
    private String email;

    @Size(min = 6, message = "Phone number invalid.")
    private String phone;

    @NotBlank(message = "Password is required.")
    @Size(min = 6, message = "Password must be at least 6 characters.")
    private String password;

}
