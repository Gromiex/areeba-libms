package com.example.libms.borrower;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/borrowers")
@RequiredArgsConstructor
public class BorrowerController {

    private final IBorrowerService service;

    @GetMapping
    public ResponseEntity<List<BorrowerDto>> getAllBorrowers() {
        return ResponseEntity.ok(service.getAllBorrowers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowerDto> getBorrower(@PathVariable Long id ) {
        return ResponseEntity.ok(service.getBorrower(id));
    }

    @PostMapping
    public ResponseEntity<BorrowerDto> createBorrower(@RequestBody BorrowerDto dto) {
        BorrowerDto created = service.createBorrower(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteBorrower(@PathVariable Long id) {
        service.deleteBorrower(id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Borrower deleted successfully");
        response.put("Borrower", id);
        response.put("timestamp", Instant.now());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BorrowerDto> updateBorrower(@PathVariable Long id, @RequestBody BorrowerDto dto) {
        BorrowerDto updatedDto = service.updateBorrower(id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }
}
