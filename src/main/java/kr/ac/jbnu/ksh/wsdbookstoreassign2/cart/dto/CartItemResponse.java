package kr.ac.jbnu.ksh.wsdbookstoreassign2.cart.dto;

import kr.ac.jbnu.ksh.wsdbookstoreassign2.cart.domain.CartItem;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CartItemResponse(
        Long id,
        Long bookId,
        BigDecimal unitPrice,
        int quantity,
        BigDecimal lineTotal
) {
    public static CartItemResponse from(CartItem ci) {
        BigDecimal total = ci.getUnitPrice().multiply(BigDecimal.valueOf(ci.getQuantity()));
        return CartItemResponse.builder()
                .id(ci.getId())
                .bookId(ci.getBookId())
                .unitPrice(ci.getUnitPrice())
                .quantity(ci.getQuantity())
                .lineTotal(total)
                .build();
    }
}
