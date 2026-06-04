package com.example.libms.book;



import com.example.libms.author.Author;
import com.example.libms.author.AuthorMapper;
import com.example.libms.author.AuthorRepository;
import com.example.libms.author.exceptions.AuthorNotFoundException;
import com.example.libms.book.dto.*;
import com.example.libms.book.enums.BookCategory;
import com.example.libms.book.exceptions.BookNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private static final Logger log = LoggerFactory.getLogger(BookService.class);
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper mapper;
    private final AuthorMapper authorMapper;
    private final RestClient restClient;

    public List<BookDto> getAllBooks(String title, BookCategory category, String authorName) {
        return bookRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public List<BookDto> searchBooks(BookSearchRequest request) {
        return bookRepository.searchBooks(request);
    }

    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("no book with id " + id));

        BookDto bookDto = mapper.toDto(book);
        bookDto.setAuthorName(book.getAuthor().getName());

        return bookDto;
    }

    public BookDto createBook(BookDto bookDto) {
        if (!authorRepository.existsById(bookDto.getAuthorId()))
            throw new AuthorNotFoundException("no author with id " + bookDto.getAuthorId());

        Book savedBook = bookRepository.save(mapper.toEntity(bookDto));
        log.info("added a new book: {}", savedBook.getTitle());
        return mapper.toDto(savedBook);
    }

    public List<BookDto> createBooks(List<BookDto> bookDtos) {

        Set<Long> authorIds = bookDtos.stream()
                .map(BookDto::getAuthorId)
                .collect(Collectors.toSet());

        Set<Long> existingAuthorIds = authorRepository.findAllById(authorIds)
                .stream()
                .map(Author::getId)
                .collect(Collectors.toSet());

        Set<Long> missingIds = authorIds.stream()
                .filter(id -> !existingAuthorIds.contains(id))
                .collect(Collectors.toSet());

        if (!missingIds.isEmpty())
            throw new AuthorNotFoundException("no authors found with ids: " + missingIds);

        List<Book> books = bookDtos.stream()
                .map(mapper::toEntity)
                .toList();

        List<Book> savedBooks = bookRepository.saveAll(books);
        log.info("added {} new books", savedBooks.size());
        return savedBooks.stream().map(mapper::toDto).toList();
    }

    public BookDto createBooksWithAuthorFetch(OpenLibraryRequestDto dto) {

        Map<String, OpenLibraryResponseDto> bookInfo = restClient.get()
                .uri("/api/books?bibkeys=ISBN:{isbn}&format=json&jscmd=data", dto.getISBN())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    throw new RuntimeException("Book not found for ISBN: " + dto.getISBN());
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                    throw new RuntimeException("OpenLibrary API unavailable, try again later");
                })
                .body(new ParameterizedTypeReference<Map<String, OpenLibraryResponseDto>>() {
                });

        assert bookInfo != null;

        String authorKey = bookInfo.get("ISBN:" + dto.getISBN()).getAuthors().getFirst().getUrl().split("/")[4];

        OpenLibraryAuthorResponseDto authorInfo = restClient.get()
                .uri("/authors/{key}.json", authorKey)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    throw new RuntimeException("Author not found for olid: " + authorKey);
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                    throw new RuntimeException("OpenLibrary API unavailable, try again later");
                })
                .body(OpenLibraryAuthorResponseDto.class);

        assert authorInfo != null;

        Author savedAuthor = authorRepository.save(authorMapper.toEntity(authorInfo));

        Book mappedBook = mapper.toEntity(bookInfo.get("ISBN:" + dto.getISBN()));
        mappedBook.setAuthor(new Author(savedAuthor.getId()));
        mappedBook.setISBN(dto.getISBN());

        Book savedBook = bookRepository.save(mappedBook);

        savedBook.getAuthor().setName(savedAuthor.getName());

        return mapper.toDto(savedBook);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("no book with id " + id);
        }

        bookRepository.deleteById(id);
        log.info("deleted book with id {}", id);
    }

    public BookDto updateBook(Long id, BookDto dto) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("no book with id " + id));

        existingBook.setISBN(dto.getISBN());
        existingBook.setAvailable(dto.isAvailable());
        existingBook.setTitle(dto.getTitle());
        existingBook.setCategory(dto.getCategory());

        if (!Objects.equals(dto.getAuthorId(), existingBook.getAuthor().getId())) {
            Author newAuthor = authorRepository.findById(dto.getAuthorId())
                    .orElseThrow(() -> new AuthorNotFoundException("no author with id " + id));

            existingBook.setAuthor(newAuthor);
        }

        return mapper.toDto(bookRepository.save(existingBook));
    }
}
