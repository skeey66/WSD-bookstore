package kr.ac.jbnu.ksh.wsdbookstoreassign2.cart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartItemAddRequest {
    @NotNull
    private Long bookId;

    @Min(1)
    private int quantity;
}
