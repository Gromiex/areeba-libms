package com.example.libms.borrowing;

import com.example.libms.book.Book;
import com.example.libms.borrower.Borrower;
import com.example.libms.borrowing.enums.BorrowingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "borrowings")
public class Borrowing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "borrower_id")
    private Borrower borrower;

    @Column(updatable = false)
    private LocalDateTime borrowedDate;

    private LocalDateTime returnedDate;

    @Enumerated(EnumType.STRING)
    private BorrowingStatus status;

    @PrePersist
    protected void onCreate() {
        this.borrowedDate = LocalDateTime.now();
    }

    public Borrowing() {}

    public Borrowing(Long id, Book book, Borrower borrower, BorrowingStatus status) {
        this.id = id;
        this.book = book;
        this.borrower = borrower;
        this.status = status;
    }

    public Borrowing(Long id, Book book, Borrower borrower, LocalDateTime borrowedDate, LocalDateTime returnedDate, BorrowingStatus status) {
        this.id = id;
        this.book = book;
        this.borrower = borrower;
        this.borrowedDate = borrowedDate;
        this.returnedDate = returnedDate;
        this.status = status;
    }

}
