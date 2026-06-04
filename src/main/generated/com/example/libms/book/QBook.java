package com.example.libms.book;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBook is a Querydsl query type for Book
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBook extends EntityPathBase<Book> {

    private static final long serialVersionUID = -452593222L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBook book = new QBook("book");

    public final com.example.libms.author.QAuthor author;

    public final BooleanPath available = createBoolean("available");

    public final ListPath<com.example.libms.borrowing.Borrowing, com.example.libms.borrowing.QBorrowing> borrowings = this.<com.example.libms.borrowing.Borrowing, com.example.libms.borrowing.QBorrowing>createList("borrowings", com.example.libms.borrowing.Borrowing.class, com.example.libms.borrowing.QBorrowing.class, PathInits.DIRECT2);

    public final EnumPath<com.example.libms.book.enums.BookCategory> category = createEnum("category", com.example.libms.book.enums.BookCategory.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath ISBN = createString("ISBN");

    public final StringPath title = createString("title");

    public QBook(String variable) {
        this(Book.class, forVariable(variable), INITS);
    }

    public QBook(Path<? extends Book> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBook(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBook(PathMetadata metadata, PathInits inits) {
        this(Book.class, metadata, inits);
    }

    public QBook(Class<? extends Book> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.author = inits.isInitialized("author") ? new com.example.libms.author.QAuthor(forProperty("author")) : null;
    }

}

