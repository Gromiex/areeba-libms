package com.example.libms.borrower;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBorrower is a Querydsl query type for Borrower
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBorrower extends EntityPathBase<Borrower> {

    private static final long serialVersionUID = -202925222L;

    public static final QBorrower borrower = new QBorrower("borrower");

    public final ListPath<com.example.libms.borrowing.Borrowing, com.example.libms.borrowing.QBorrowing> borrowings = this.<com.example.libms.borrowing.Borrowing, com.example.libms.borrowing.QBorrowing>createList("borrowings", com.example.libms.borrowing.Borrowing.class, com.example.libms.borrowing.QBorrowing.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath phone = createString("phone");

    public QBorrower(String variable) {
        super(Borrower.class, forVariable(variable));
    }

    public QBorrower(Path<? extends Borrower> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBorrower(PathMetadata metadata) {
        super(Borrower.class, metadata);
    }

}

