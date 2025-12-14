package kr.ac.jbnu.ksh.wsdbookstoreassign2.cart.repository;

import kr.ac.jbnu.ksh.wsdbookstoreassign2.cart.domain.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @EntityGraph(attributePaths = "items")
    Optional<Cart> findByUserIdAndDeletedAtIsNull(Long userId);
}