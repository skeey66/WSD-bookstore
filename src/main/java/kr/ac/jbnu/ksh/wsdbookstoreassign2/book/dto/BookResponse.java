package kr.ac.jbnu.ksh.wsdbookstoreassign2.book.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record BookResponse(
        Long id,
        String isbn,
        String publisher,
        String title,
        String summary,
        BigDecimal price,
        Integer publicationYear,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
