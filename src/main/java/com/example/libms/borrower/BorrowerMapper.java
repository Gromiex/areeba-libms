package com.example.libms.borrower;

import org.springframework.stereotype.Component;

@Component
public class BorrowerMapper {

    public BorrowerDto toDto(Borrower borrower) {
        return new BorrowerDto(
                borrower.getId(),
                borrower.getName(),
                borrower.getEmail(),
                borrower.getPassword(),
                borrower.getPhone()
        );
    }

    public Borrower toEntity(BorrowerDto dto) {
        Borrower borrower = new Borrower();
        borrower.setId(dto.getId());
        borrower.setName(dto.getName());
        borrower.setEmail(dto.getEmail());
        borrower.setPassword(dto.getPassword());
        borrower.setPhone(dto.getPhone());
        return borrower;
    }
}
