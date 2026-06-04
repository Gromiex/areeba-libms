package com.example.libms.book;

import java.util.List;

public interface BookRepositoryCustom {
    List<BookDto> searchBooks(BookSearchRequest request);
}
