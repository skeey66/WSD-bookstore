package kr.ac.jbnu.ksh.wsdbookstoreassign2.order.service;

import kr.ac.jbnu.ksh.wsdbookstoreassign2.book.domain.Book;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.book.repository.BookRepository;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.order.domain.*;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.order.dto.*;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.order.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.common.exception.NotFoundException;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.common.exception.ConflictException;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.common.exception.ForbiddenException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final CurrentUserResolver currentUser;

    private static final Map<OrderStatus, EnumSet<OrderStatus>> ALLOWED = Map.of(
            OrderStatus.CREATED,   EnumSet.of(OrderStatus.PAID, OrderStatus.CANCELLED),
            OrderStatus.PAID,      EnumSet.of(OrderStatus.SHIPPED, OrderStatus.CANCELLED),
            OrderStatus.SHIPPED,   EnumSet.of(OrderStatus.DELIVERED),
            OrderStatus.DELIVERED, EnumSet.noneOf(OrderStatus.class),
            OrderStatus.CANCELLED, EnumSet.noneOf(OrderStatus.class)
    );

    public OrderResponse create(Authentication auth, OrderCreateRequest req) {
        Long userId = currentUser.currentUserId(auth);

        Order order = Order.builder()
                .userId(userId)
                .status(OrderStatus.CREATED)
                .totalAmount(BigDecimal.ZERO)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        BigDecimal total = BigDecimal.ZERO;

        for (var itemReq : req.getItems()) {
            Book book = bookRepository.findById(itemReq.getBookId())
                    .orElseThrow(() -> new NotFoundException("Book not found. id=" + itemReq.getBookId()));

            BigDecimal unitPrice = book.getPrice(); // 네 Book 필드명에 맞게 수정
            if (unitPrice == null) throw new ConflictException("Book price is null. id=" + book.getId());

            OrderItem oi = OrderItem.builder()
                    .bookId(book.getId())
                    .unitPrice(unitPrice)
                    .quantity(itemReq.getQuantity())
                    .build();

            order.addItem(oi);
            total = total.add(unitPrice.multiply(BigDecimal.valueOf(itemReq.getQuantity())));
        }

        order.setTotalAmount(total);

        return OrderResponse.from(orderRepository.save(order));
    }

    @Transactional(readOnly = true)
    public OrderResponse get(Authentication auth, Long id) {
        Order order = orderRepository.findById(id)
                .filter(o -> o.getDeletedAt() == null)
                .orElseThrow(() -> new NotFoundException("Order not found. id=" + id));

        enforceOwnerOrAdmin(auth, order);
        return OrderResponse.from(order);
    }

    @Transactional(readOnly = true)
    public Page<OrderResponse> list(Authentication auth, OrderStatus status, Pageable pageable) {
        Long userId = currentUser.isAdmin(auth) ? null : currentUser.currentUserId(auth);

        Specification<Order> spec = Specification.where(OrderSpecifications.notDeleted())
                .and(OrderSpecifications.userIdEq(userId))
                .and(OrderSpecifications.statusEq(status));

        return orderRepository.findAll(spec, pageable).map(OrderResponse::from);
    }

    public OrderResponse cancel(Authentication auth, Long id) {
        Order order = orderRepository.findById(id)
                .filter(o -> o.getDeletedAt() == null)
                .orElseThrow(() -> new NotFoundException("Order not found. id=" + id));

        enforceOwnerOrAdmin(auth, order);

        if (!EnumSet.of(OrderStatus.CREATED, OrderStatus.PAID).contains(order.getStatus())) {
            throw new ConflictException("Cannot cancel when status=" + order.getStatus());
        }

        order.setStatus(OrderStatus.CANCELLED);
        order.setUpdatedAt(LocalDateTime.now());
        return OrderResponse.from(order);
    }

    public OrderResponse updateStatus(Authentication auth, Long id, OrderStatus next) {
        if (!currentUser.isAdmin(auth)) throw new ForbiddenException("ADMIN only.");

        Order order = orderRepository.findById(id)
                .filter(o -> o.getDeletedAt() == null)
                .orElseThrow(() -> new NotFoundException("Order not found. id=" + id));

        OrderStatus curr = order.getStatus();
        if (!ALLOWED.getOrDefault(curr, EnumSet.noneOf(OrderStatus.class)).contains(next)) {
            throw new ConflictException("Invalid status transition: " + curr + " -> " + next);
        }

        order.setStatus(next);
        order.setUpdatedAt(LocalDateTime.now());
        return OrderResponse.from(order);
    }

    private void enforceOwnerOrAdmin(Authentication auth, Order order) {
        if (currentUser.isAdmin(auth)) return;
        Long me = currentUser.currentUserId(auth);
        if (!order.getUserId().equals(me)) throw new ForbiddenException("Not your order.");
    }
}
