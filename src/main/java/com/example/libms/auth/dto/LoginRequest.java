package com.example.libms.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRequest {

    @NotNull(message = "email is required")
    private String email;

    @NotNull(message = "authorId is required")
    private String password;
}
