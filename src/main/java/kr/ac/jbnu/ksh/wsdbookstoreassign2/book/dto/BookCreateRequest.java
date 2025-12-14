package kr.ac.jbnu.ksh.wsdbookstoreassign2.book.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record BookCreateRequest(
        @NotBlank @Size(max = 50) String isbn,
        @NotBlank @Size(max = 255) String publisher,
        @NotBlank @Size(max = 255) String title,
        @NotBlank String summary,
        @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal price,
        @NotNull @Min(0) Integer publicationYear
) {}
