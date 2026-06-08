package com.example.libms.borrowing;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/borrowing")
@RequiredArgsConstructor
public class BorrowingController {
    private final IBorrowingService borrowingService;

    @GetMapping
    public ResponseEntity<List<BorrowingDto>> getAllBorrowings() {
        return ResponseEntity.ok(borrowingService.getAllBorrowings());
    }

    @PostMapping
    public ResponseEntity<BorrowingDto> createBorrowing(@RequestBody BorrowingDto dto) {
        BorrowingDto borrowingDto = borrowingService.createBorrowing(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(borrowingDto);
    }

    @PatchMapping("/{id}/return")
    public ResponseEntity<BorrowingDto> returnBorrowing(@PathVariable Long id) {
        BorrowingDto dto = borrowingService.returnBorrowing(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteBorrowing(@PathVariable Long id) {
        borrowingService.deleteBorrowing(id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Borrowing deleted successfully");
        response.put("Borrowing", id);
        response.put("timestamp", Instant.now());

        return ResponseEntity.ok(response);
    }
}
