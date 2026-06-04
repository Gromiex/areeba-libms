package com.example.libms.config;

import com.example.libms.book.properties.OpenLibraryProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    private final OpenLibraryProperties openLibraryProperties;

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl(openLibraryProperties.getBaseurl())
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
