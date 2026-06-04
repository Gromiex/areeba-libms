package com.example.libms.book.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "openlibrary.api")
public class OpenLibraryProperties {
    private String baseurl;
}
