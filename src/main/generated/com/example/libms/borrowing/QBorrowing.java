package com.example.libms.borrowing;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBorrowing is a Querydsl query type for Borrowing
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBorrowing extends EntityPathBase<Borrowing> {

    private static final long serialVersionUID = 32343704L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBorrowing borrowing = new QBorrowing("borrowing");

    public final com.example.libms.book.QBook book;

    public final DateTimePath<java.time.LocalDateTime> borrowedDate = createDateTime("borrowedDate", java.time.LocalDateTime.class);

    public final com.example.libms.borrower.QBorrower borrower;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> returnedDate = createDateTime("returnedDate", java.time.LocalDateTime.class);

    public final EnumPath<com.example.libms.borrowing.enums.BorrowingStatus> status = createEnum("status", com.example.libms.borrowing.enums.BorrowingStatus.class);

    public QBorrowing(String variable) {
        this(Borrowing.class, forVariable(variable), INITS);
    }

    public QBorrowing(Path<? extends Borrowing> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBorrowing(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBorrowing(PathMetadata metadata, PathInits inits) {
        this(Borrowing.class, metadata, inits);
    }

    public QBorrowing(Class<? extends Borrowing> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.book = inits.isInitialized("book") ? new com.example.libms.book.QBook(forProperty("book"), inits.get("book")) : null;
        this.borrower = inits.isInitialized("borrower") ? new com.example.libms.borrower.QBorrower(forProperty("borrower")) : null;
    }

}

