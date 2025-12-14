package kr.ac.jbnu.ksh.wsdbookstoreassign2.cart.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartItemUpdateRequest {
    @Min(1)
    private int quantity;
}
