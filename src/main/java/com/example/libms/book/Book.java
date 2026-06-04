package com.example.libms.book;

import com.example.libms.author.Author;
import com.example.libms.book.enums.BookCategory;
import com.example.libms.borrowing.Borrowing;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "category is mandatory")
    @Enumerated(EnumType.STRING)
    private BookCategory category;

    @NotBlank(message = "title is mandatory")
    private String title;

    @NotBlank(message = "isbn is mandatory")
    @Column(unique = true)
    private String ISBN;

    private boolean available;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Borrowing> borrowings;

    public Book() {}

    public Book(Long id) {
        this.id = id;
    }

    public Book(Long id, BookCategory category, String title, String ISBN, boolean available, Author author) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.ISBN = ISBN;
        this.available = available;
        this.author = author;
    }

}
