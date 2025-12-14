package kr.ac.jbnu.ksh.wsdbookstoreassign2.order.repository;

import kr.ac.jbnu.ksh.wsdbookstoreassign2.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
}
