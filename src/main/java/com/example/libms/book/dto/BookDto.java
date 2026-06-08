package com.example.libms.book.dto;

import com.example.libms.book.enums.BookCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookDto {
    private Long id;

    @NotNull(message = "category is mandatory")
    private BookCategory category;

    @NotBlank(message = "title is mandatory")
    private String title;

    @NotBlank(message = "ISBN is mandatory")
    private String ISBN;

    private boolean available;

    private Long authorId;
    private String authorName;

    public BookDto() {}

    public BookDto(Long id, BookCategory category, String title, String ISBN, boolean available, Long authorId) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.ISBN = ISBN;
        this.available = available;
        this.authorId = authorId;
    }

    public BookDto(Long id, BookCategory category, String title, String ISBN, boolean available, Long authorId, String authorName) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.ISBN = ISBN;
        this.available = available;
        this.authorId = authorId;
        this.authorName = authorName;
    }

}
