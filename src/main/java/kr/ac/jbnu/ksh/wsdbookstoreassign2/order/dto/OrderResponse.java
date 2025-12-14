package kr.ac.jbnu.ksh.wsdbookstoreassign2.order.dto;

import kr.ac.jbnu.ksh.wsdbookstoreassign2.order.domain.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class OrderResponse {
    private Long id;
    private Long userId;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Item> items;

    public static OrderResponse from(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .items(order.getItems().stream().map(Item::from).toList())
                .build();
    }

    @Getter @Builder
    public static class Item {
        private Long id;
        private Long bookId;
        private BigDecimal unitPrice;
        private int quantity;

        public static Item from(OrderItem oi) {
            return Item.builder()
                    .id(oi.getId())
                    .bookId(oi.getBookId())
                    .unitPrice(oi.getUnitPrice())
                    .quantity(oi.getQuantity())
                    .build();
        }
    }
}
