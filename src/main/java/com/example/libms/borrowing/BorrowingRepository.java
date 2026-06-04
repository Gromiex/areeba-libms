package com.example.libms.borrowing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BorrowingRepository extends JpaRepository<Borrowing, Long>, QuerydslPredicateExecutor<Borrowing>, BorrowingRepositoryCustom {
}
