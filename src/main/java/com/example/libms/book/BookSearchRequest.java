package com.example.libms.book;

import com.example.libms.book.enums.BookCategory;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookSearchRequest {
    private String title;
    private BookCategory category;
    private Long authorId;
    private String authorName;

    public BookSearchRequest() {}

    public BookSearchRequest(String title, BookCategory category, String authorName, Long authorId) {
        this.title = title;
        this.category = category;
        this.authorName = authorName;
        this.authorId = authorId;
    }

}
