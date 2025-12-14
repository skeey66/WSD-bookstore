package kr.ac.jbnu.ksh.wsdbookstoreassign2.order.repository;

import kr.ac.jbnu.ksh.wsdbookstoreassign2.order.domain.Order;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.order.domain.OrderStatus;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecifications {
    public static Specification<Order> notDeleted() {
        return (root, query, cb) -> cb.isNull(root.get("deletedAt"));
    }
    public static Specification<Order> userIdEq(Long userId) {
        if (userId == null) return null;
        return (root, query, cb) -> cb.equal(root.get("userId"), userId);
    }
    public static Specification<Order> statusEq(OrderStatus status) {
        if (status == null) return null;
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }
}
