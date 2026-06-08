package com.example.libms.borrowing;

import com.example.libms.book.Book;
import com.example.libms.book.BookRepository;
import com.example.libms.borrower.BorrowerRepository;
import com.example.libms.borrower.properties.BorrowerTransactionProperties;
import com.example.libms.borrowing.enums.BorrowingStatus;
import com.example.libms.borrowing.exceptions.BookAlreadyReturnedException;
import com.example.libms.borrowing.exceptions.BookingLimitReachedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BorrowingServiceTest {
    @Mock BorrowingRepository borrowingRepository;
    @Mock
    BookRepository bookRepository;
    @Mock
    BorrowerRepository borrowerRepository;
    @Mock
    BorrowerTransactionProperties borrowerTransactionProperties;
    @InjectMocks
    BorrowingServiceImpl borrowingService;
    @Test
    void createBorrowing_shouldThrow_whenLimitReached() {
        when(borrowingRepository.countBorrowerBorrowings(1L)).thenReturn(5);
        when(borrowerTransactionProperties.getLimit()).thenReturn(5);
        BorrowingDto dto = new BorrowingDto();
        dto.setBorrowerId(1L);
        assertThrows(BookingLimitReachedException.class,
                () -> borrowingService.createBorrowing(dto));
    }
    @Test
    void returnBorrowing_shouldThrow_whenAlreadyReturned() {
        Borrowing b = new Borrowing();
        b.setStatus(BorrowingStatus.RETURNED);
        b.setBook(new Book(2L));
        when(borrowingRepository.findById(1L)).thenReturn(Optional.of(b));
        when(bookRepository.findById(2L)).thenReturn(Optional.of(new Book()));

        assertThrows(BookAlreadyReturnedException.class,
                () -> borrowingService.returnBorrowing(1L));
    }
}
