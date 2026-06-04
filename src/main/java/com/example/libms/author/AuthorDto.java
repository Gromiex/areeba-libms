package com.example.libms.author;


import com.example.libms.book.dto.BookDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class AuthorDto {

    @Setter
    @Getter
    private Long id;

    @Setter
    @Getter
    @NotBlank(message = "name is mandatory")
    private String name;

    @Setter
    @Getter
    @NotBlank(message = "biography is mandatory")
    private String biography;

    private List<BookDto> books;

    public AuthorDto(){}

    public AuthorDto(Long id, String name, String biography) {
        this.id = id;
        this.name = name;
        this.biography = biography;
    }

}
