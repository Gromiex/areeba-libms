package com.example.libms.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "rsa")
public class RsaKeyProperties {
    private String publicKeyPath;
    private String privateKeyPath;
}
