package com.example.libms.borrower;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IBorrowerService extends UserDetailsService {
    List<BorrowerDto> getAllBorrowers();

    BorrowerDto getBorrower(Long id);

    BorrowerDto createBorrower(BorrowerDto dto);

    void deleteBorrower(Long id);

    BorrowerDto updateBorrower(Long id, BorrowerDto dto);
}
