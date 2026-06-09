package com.example.libms.borrowing;


import com.example.libms.book.Book;
import com.example.libms.book.BookRepository;
import com.example.libms.book.exceptions.BookNotFoundException;
import com.example.libms.borrower.Borrower;
import com.example.libms.borrower.BorrowerRepository;
import com.example.libms.borrower.exceptions.BorrowerNotFoundException;
import com.example.libms.borrower.properties.BorrowerTransactionProperties;
import com.example.libms.borrowing.enums.BorrowingStatus;
import com.example.libms.borrowing.exceptions.*;
import com.example.libms.email.EmailRequestDto;
import com.example.libms.email.EmailServiceClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowingServiceImpl implements IBorrowingService {
    private static final Logger log = LoggerFactory.getLogger(BorrowingServiceImpl.class);
    private final BorrowingRepository borrowingRepository;
    private final BorrowerRepository borrowerRepository;
    private final BookRepository bookRepository;
    private final BorrowingMapper mapper;
    private final BorrowerTransactionProperties borrowerTransactionProperties;
    private final EmailServiceClient emailServiceClient;

    @Override
    public List<BorrowingDto> getAllBorrowings() {
        return borrowingRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public BorrowingDto createBorrowing(BorrowingDto dto) {
        int numberOfBorrowings = borrowingRepository.countBorrowerBorrowings(dto.getBorrowerId());

        if (numberOfBorrowings >= borrowerTransactionProperties.getLimit()) {
            throw new BookingLimitReachedException("booking limits reached for borrower with id " + dto.getBorrowerId());
        }

        Borrower borrower = borrowerRepository.findById(dto.getBorrowerId())
                .orElseThrow(() -> new BorrowerNotFoundException("no borrower with id " + dto.getBorrowerId()));

        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new BookNotFoundException("no book with id " + dto.getBookId()));

        if (!book.isAvailable())
            throw new BookUnavailableException("book with id " + book.getId() + " is unavailable for booking");

        dto.setStatus(BorrowingStatus.BORROWED);

        Borrowing borrowing = borrowingRepository.save(mapper.toEntity(dto, book, borrower));

        if (borrowing.getId() == null)
            throw new RuntimeException("failed to create borrowing");

        book.setAvailable(false);

        try {
            bookRepository.save(book);
        } catch (Exception e) {
            log.info("failed to update the book status, cancelling the borrowing");
            throw new FailedBookStatusUpdateException("failed to update book availability to false");
        }

        log.info("borrower with id {} borrowed book {} with id {}", borrowing.getId(), book.getTitle(), book.getId());

        return mapper.toDto(borrowing);
    }

    @Override
    @Transactional(readOnly = true)
    public void sendBorrowingEmail(Long id) {
        Borrowing borrowing = borrowingRepository.findById(id)
                .orElseThrow(() -> new BorrowingNotFoundException("no borrowing with id " + id));

        emailServiceClient.sendEmail(
                new EmailRequestDto(
                        borrowing.getBorrower().getEmail(),
                        "Book " + borrowing.getBook().getTitle() + " borrowed successfully",
                        borrowing.getId(),
                        borrowing.getBook().getId(),
                        borrowing.getBorrower().getId()
                )
        );
    }

    @Override
    public BorrowingDto returnBorrowing(Long id) {

        Borrowing borrowing = borrowingRepository.findById(id)
                .orElseThrow(() -> new BorrowingNotFoundException("no borrowing with id " + id));

        Book book = bookRepository.findById(borrowing.getBook().getId())
                .orElseThrow(() -> new BookNotFoundException("no book with id " + borrowing.getBook().getId()));

        if (borrowing.getStatus() == BorrowingStatus.RETURNED)
            throw new BookAlreadyReturnedException("book with id " + book.getId() + " already returned");

        borrowing.setStatus(BorrowingStatus.RETURNED);
        borrowing.setReturnedDate(LocalDateTime.now());
        Borrowing savedBorrowing = borrowingRepository.save(borrowing);

        book.setAvailable(true);

        try {
            bookRepository.save(book);
        } catch (Exception e) {
            throw new FailedBookStatusUpdateException("failed to update book availability to true");
        }

        log.info("borrower with id {} returned book {} with id {}", borrowing.getId(), book.getTitle(), book.getId());

        return mapper.toDto(savedBorrowing);
    }

    @Override
    public void deleteBorrowing(Long id) {
        Borrowing borrowing = borrowingRepository.findById(id)
                        .orElseThrow(() -> new BorrowingNotFoundException("no borrowing with id " + id));

        Book borrowedBook = bookRepository.findById(borrowing.getBook().getId())
                .orElseThrow(() -> new BookNotFoundException("no book with id " + borrowing.getBook().getId()));

        borrowedBook.setAvailable(true);
        bookRepository.save(borrowedBook);

        borrowingRepository.deleteById(id);

        log.info("borrowing with id {} has been deleted", borrowing.getId());
    }
}
