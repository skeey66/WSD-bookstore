package kr.ac.jbnu.ksh.wsdbookstoreassign2.cart.repository;

import kr.ac.jbnu.ksh.wsdbookstoreassign2.cart.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartIdAndBookId(Long cartId, Long bookId);
}
