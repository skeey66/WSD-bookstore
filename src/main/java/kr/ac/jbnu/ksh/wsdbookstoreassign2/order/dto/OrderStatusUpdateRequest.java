package kr.ac.jbnu.ksh.wsdbookstoreassign2.order.dto;

import jakarta.validation.constraints.NotNull;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.order.domain.OrderStatus;
import lombok.Getter;

@Getter
public class OrderStatusUpdateRequest {
    @NotNull
    private OrderStatus status;
}
