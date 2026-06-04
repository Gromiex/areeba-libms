package com.example.libms.book;

import com.example.libms.book.dto.BookDto;
import com.example.libms.book.dto.OpenLibraryRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService service;

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks(@ModelAttribute BookSearchRequest request) {
        return ResponseEntity.ok(service.searchBooks(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        BookDto bookDto = service.getBookById(id);
        return ResponseEntity.status(HttpStatus.OK).body(bookDto);
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        BookDto created = service.createBook(bookDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<BookDto>> createBooks(@RequestBody List<BookDto> bookDtos) {
        List<BookDto> created = service.createBooks(bookDtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("openLibrary")
    public ResponseEntity<BookDto> createBooksWithAuthorFetch(@RequestBody OpenLibraryRequestDto dto) {
        BookDto created = service.createBooksWithAuthorFetch(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteBook(@PathVariable Long id) {
        service.deleteBook(id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "book deleted successfully");
        response.put("bookId", id);
        response.put("timestamp", Instant.now());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody BookDto dto) {
        BookDto updatedBookDto = service.updateBook(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedBookDto);
    }
}
