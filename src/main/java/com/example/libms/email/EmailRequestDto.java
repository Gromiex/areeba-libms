package com.example.libms.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequestDto {
    private String toEmail;
    private String message;
    private Long borrowingId;
    private Long bookId;
    private Long borrowerId;
}