package com.example.libms.book.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenLibraryResponseDto {

    @NotBlank(message = "title is mandatory")
    private String title;

    private List<OpenLibrarySubjectDto> subjects;

    @NotEmpty(message = "Authors list must not be empty")
    private List<OpenLibraryAuthorDto> authors;

    private OpenLibraryIdentifiersDto identifiers;

}
