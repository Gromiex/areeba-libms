package com.example.libms.email;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "email-service", url = "${services.email.url}")
public interface EmailServiceClient {

    @PostMapping("/email/send-email")
    void sendEmail(@RequestBody EmailRequestDto request);
}