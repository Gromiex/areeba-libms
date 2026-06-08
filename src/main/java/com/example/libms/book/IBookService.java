package com.example.libms.book;

import com.example.libms.book.dto.BookDto;
import com.example.libms.book.dto.OpenLibraryRequestDto;

import java.util.List;

public interface IBookService {
     List<BookDto> searchBooks(BookSearchRequest request);
    BookDto getBookById(Long id);
    BookDto createBook(BookDto bookDto);
    List<BookDto> createBooks(List<BookDto> bookDtos);
    BookDto createBooksWithAuthorFetch(OpenLibraryRequestDto dto);
    void deleteBook(Long id);
    BookDto updateBook(Long id, BookDto dto);
}
