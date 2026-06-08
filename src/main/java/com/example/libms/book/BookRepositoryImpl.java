package com.example.libms.book;

import com.example.libms.author.QAuthor;
import com.example.libms.book.dto.BookDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Repository
public class BookRepositoryImpl implements BookRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<BookDto> searchBooks(BookSearchRequest request) {
        QBook book = QBook.book;
        QAuthor author = QAuthor.author;

        JPAQuery<BookDto> query = new JPAQuery<>(em)
                .select(Projections.constructor(BookDto.class,
                        book.id,
                        book.category,
                        book.title,
                        book.ISBN,
                        book.available,
                        author.id,
                        author.name
                ))
                .from(book)
                .join(book.author, author); // inner join

        BooleanBuilder where = new BooleanBuilder();

        if (request.getAuthorId() != null) {
            where.and(book.author.id.eq(request.getAuthorId()));
        }
        if (StringUtils.hasText(request.getTitle())) {
            where.and(book.title.containsIgnoreCase(request.getTitle()));
        }
        if (request.getCategory() != null) {
            where.and(book.category.eq(request.getCategory()));
        }
        if (StringUtils.hasText(request.getAuthorName())) {
            where.and(author.name.containsIgnoreCase(request.getAuthorName()));
        }

        return query.where(where).fetch();
    }

    @Override
    public int countAuthorBooks(Long authorId) {
        QBook book = QBook.book;

        Long count = new JPAQuery<Long>(em)
                .select(book.count())
                .from(book)
                .where(book.author.id.eq(authorId))
                .fetchOne();

        return Math.toIntExact(Objects.requireNonNullElse(count, 0L));
    }
}
