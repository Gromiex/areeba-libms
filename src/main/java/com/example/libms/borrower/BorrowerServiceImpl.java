package com.example.libms.borrower;

import com.example.libms.borrower.exceptions.BorrowerHasActiveBorrowingsException;
import com.example.libms.borrower.exceptions.BorrowerNotFoundException;
import com.example.libms.borrowing.BorrowingRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BorrowerServiceImpl implements IBorrowerService {
    private static final Logger log = LoggerFactory.getLogger(BorrowerServiceImpl.class);
    private final BorrowerRepository borrowerRepository;
    private final BorrowingRepository borrowingRepository;
    private final BorrowerMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<BorrowerDto> getAllBorrowers() {
        return borrowerRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public BorrowerDto getBorrower(Long id) {
        Borrower borrower = borrowerRepository.findById(id)
                .orElseThrow(() -> new BorrowerNotFoundException("no borrower with id " + id));

        return mapper.toDto(borrower);
    }

    @Override
    public BorrowerDto createBorrower(BorrowerDto dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        Borrower saved = borrowerRepository.save(mapper.toEntity(dto));
        log.info("added a new borrower {}", saved.getName());
        return mapper.toDto(saved);
    }

    @Override
    public void deleteBorrower(Long id) {
        if (!borrowerRepository.existsById(id))
            throw new BorrowerNotFoundException("no borrower with id " + id);

        int activeBorrowings = borrowingRepository.countBorrowerBorrowings(id);
        if (activeBorrowings > 0) {
            throw new BorrowerHasActiveBorrowingsException("cannot delete borrower with id " + id + " because they have " + activeBorrowings + " active borrowings");
        }

        borrowerRepository.deleteById(id);
        log.info("deleted borrower with id {}", id);
    }

    @Override
    public BorrowerDto updateBorrower(Long id, BorrowerDto dto) {
        Borrower existing = borrowerRepository.findById(id)
                .orElseThrow(() -> new BorrowerNotFoundException("no borrower with id " + id));

        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setPhone(dto.getPhone());

        return mapper.toDto(borrowerRepository.save(existing));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return borrowerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Borrower not found: " + email));
    }
}
