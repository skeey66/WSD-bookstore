package kr.ac.jbnu.ksh.wsdbookstoreassign2.wishlist.repository;

import kr.ac.jbnu.ksh.wsdbookstoreassign2.wishlist.domain.Wishlist;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.wishlist.domain.WishlistId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, WishlistId> {
    List<Wishlist> findAllByIdUserIdOrderByCreatedAtDesc(Long userId);
    long countByIdUserId(Long userId);
}
