package com.example.libms.book;

import com.example.libms.book.dto.BookDto;

import java.util.List;

public interface BookRepositoryCustom {
    List<BookDto> searchBooks(BookSearchRequest request);
    int countAuthorBooks(Long authorId);
}
