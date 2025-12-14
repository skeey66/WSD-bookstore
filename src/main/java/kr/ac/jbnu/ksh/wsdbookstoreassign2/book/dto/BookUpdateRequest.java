package kr.ac.jbnu.ksh.wsdbookstoreassign2.book.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record BookUpdateRequest(
        @Size(max = 50) String isbn,
        @Size(max = 255) String publisher,
        @Size(max = 255) String title,
        String summary,
        @DecimalMin(value = "0.0", inclusive = false) BigDecimal price,
        @Min(0) Integer publicationYear
) {}
