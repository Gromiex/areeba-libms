package com.example.libms.borrowing;

import com.example.libms.borrowing.enums.BorrowingStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class BorrowingDto {

    private Long id;

    @NotNull(message = "bookId is required")
    @Positive
    private Long bookId;

    @NotNull(message = "borrowerId is required")
    @Positive
    private Long borrowerId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime borrowedDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime returnedDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BorrowingStatus status;

    public BorrowingDto() {}

    public BorrowingDto(Long id, Long bookId, Long borrowerId, LocalDateTime borrowedDate, LocalDateTime returnedDate, BorrowingStatus status) {
        this.id = id;
        this.bookId = bookId;
        this.borrowerId = borrowerId;
        this.borrowedDate = borrowedDate;
        this.returnedDate = returnedDate;
        this.status = status;
    }

}
