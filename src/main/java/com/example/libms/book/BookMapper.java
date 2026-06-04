package com.example.libms.book;

import com.example.libms.author.Author;
import com.example.libms.book.dto.BookDto;
import com.example.libms.book.dto.OpenLibraryIdentifiersDto;
import com.example.libms.book.dto.OpenLibraryResponseDto;
import com.example.libms.book.enums.BookCategory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookMapper {

    public BookDto toDto(Book book) {
        return new BookDto (
                book.getId(),
                book.getCategory(),
                book.getTitle(),
                book.getISBN(),
                book.isAvailable(),
                book.getAuthor().getId(),
                book.getAuthor().getName()
        );
    }

    public Book toEntity(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setCategory(bookDto.getCategory());
        book.setTitle(bookDto.getTitle());
        book.setISBN(bookDto.getISBN());
        book.setAvailable(bookDto.isAvailable());
        book.setAuthor(new Author(bookDto.getAuthorId()));
        return book;
    }

    public Book toEntity(OpenLibraryResponseDto dto) {
        Book book = new Book();
        book.setAvailable(true);
        book.setTitle(dto.getTitle());

        book.setCategory(
                dto.getSubjects().stream()
                        .map(subject -> BookCategory.fromSubjectName(subject.getName()))
                        .filter(category -> category != BookCategory.UNCATEGORIZED)
                        .findFirst()
                        .orElse(BookCategory.UNCATEGORIZED)
        );

        return book;
    }

}
