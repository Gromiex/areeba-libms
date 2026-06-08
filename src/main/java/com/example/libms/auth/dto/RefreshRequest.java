package com.example.libms.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RefreshRequest {

    @NotNull(message = "refresh token is required")
    private String refreshToken;
}
