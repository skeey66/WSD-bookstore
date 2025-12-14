package kr.ac.jbnu.ksh.wsdbookstoreassign2.wishlist.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record WishlistItemResponse(
        Long bookId,
        String title,
        String publisher,
        BigDecimal price,
        LocalDateTime wishedAt
) { }
