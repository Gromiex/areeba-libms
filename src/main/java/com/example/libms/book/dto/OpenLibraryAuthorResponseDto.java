package com.example.libms.book.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenLibraryAuthorResponseDto {
    private String key;
    private String name;
    private OpenLibraryAuthorBioDto bio;
}
