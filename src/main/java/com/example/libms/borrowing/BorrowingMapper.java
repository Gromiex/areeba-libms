package com.example.libms.borrowing;

import com.example.libms.book.Book;
import com.example.libms.borrower.Borrower;
import org.springframework.stereotype.Component;

@Component
public class BorrowingMapper {

    public BorrowingDto toDto(Borrowing borrowing) {
        return new BorrowingDto(
                borrowing.getId(),
                borrowing.getBook().getId(),
                borrowing.getBorrower().getId(),
                borrowing.getBorrowedDate(),
                borrowing.getReturnedDate(),
                borrowing.getStatus()
        );
    }

    public Borrowing toEntity(BorrowingDto dto) {
        Borrowing borrowing = new Borrowing();

        borrowing.setId(dto.getId());
        borrowing.setBook(new Book(dto.getBookId()));
        borrowing.setBorrower(new Borrower(dto.getBorrowerId()));
        borrowing.setBorrowedDate(dto.getBorrowedDate());
        borrowing.setReturnedDate(dto.getReturnedDate());
        borrowing.setStatus(dto.getStatus());

        return borrowing;
    }
}
