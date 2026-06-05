package com.example.libms.borrower;

import com.example.libms.borrower.exceptions.BorrowerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BorrowerService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(BorrowerService.class);
    private final BorrowerRepository borrowerRepository;
    private final BorrowerMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public List<BorrowerDto> getAllBorrowers() {
        return borrowerRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public BorrowerDto getBorrower(Long id) {
        Borrower borrower = borrowerRepository.findById(id)
                .orElseThrow(() -> new BorrowerNotFoundException("no borrower with id " + id));

        return mapper.toDto(borrower);
    }

    public BorrowerDto createBorrower(BorrowerDto dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        Borrower saved = borrowerRepository.save(mapper.toEntity(dto));
        log.info("added a new borrower {}", saved.getName());
        return mapper.toDto(saved);
    }

    public void deleteBorrower(Long id) {
        if (!borrowerRepository.existsById(id))
            throw new BorrowerNotFoundException("no borrower with id " + id);

        borrowerRepository.deleteById(id);
        log.info("deleted borrower with id {}", id);
    }

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
