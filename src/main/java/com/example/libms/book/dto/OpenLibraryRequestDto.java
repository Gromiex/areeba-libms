package com.example.libms.book.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpenLibraryRequestDto {

    private String ISBN;

    public OpenLibraryRequestDto(String ISBN) {
        this.ISBN = ISBN;
    }
}
