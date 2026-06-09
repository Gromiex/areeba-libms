package com.example.libms.borrowing;

import java.util.List;

public interface IBorrowingService {
    List<BorrowingDto> getAllBorrowings();

    BorrowingDto createBorrowing(BorrowingDto dto);

    void sendBorrowingEmail(Long id);

    BorrowingDto returnBorrowing(Long id);

    void deleteBorrowing(Long id);
}
