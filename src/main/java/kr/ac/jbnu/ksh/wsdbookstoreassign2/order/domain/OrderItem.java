package kr.ac.jbnu.ksh.wsdbookstoreassign2.order.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name="order_items")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="order_id")
    private Order order;

    @Column(name="book_id", nullable=false)
    private Long bookId;

    @Column(name="unit_price", nullable=false, precision=15, scale=2)
    private BigDecimal unitPrice;

    @Column(nullable=false)
    private int quantity;
}
