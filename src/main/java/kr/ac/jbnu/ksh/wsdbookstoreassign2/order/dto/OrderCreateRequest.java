package kr.ac.jbnu.ksh.wsdbookstoreassign2.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderCreateRequest {

    @NotEmpty
    private List<@Valid Item> items;

    @Getter
    public static class Item {
        @NotNull
        private Long bookId;

        @NotNull @Positive
        private Integer quantity;
    }
}
