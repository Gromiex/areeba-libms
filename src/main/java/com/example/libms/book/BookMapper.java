package com.example.libms.book;

import com.example.libms.author.Author;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookDto toDto(Book book) {
        return new BookDto (
                book.getId(),
                book.getCategory(),
                book.getTitle(),
                book.getISBN(),
                book.isAvailable(),
                book.getAuthor().getId()
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

}
