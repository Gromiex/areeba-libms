package com.example.libms.borrowing;

import com.example.libms.borrowing.enums.BorrowingStatus;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class BorrowingRepositoryImpl implements BorrowingRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public int countBorrowerBorrowings(Long id) {
        QBorrowing borrowing = QBorrowing.borrowing;

        Long count = new JPAQuery<Long>(em)
                .select(borrowing.count())
                .from(borrowing)
                .where(
                        borrowing.borrower.id.eq(id),
                        borrowing.status.eq(BorrowingStatus.BORROWED)
                )
                .fetchOne();

        return Math.toIntExact(Objects.requireNonNullElse(count, 0L));
    }
}
