package kr.ac.jbnu.ksh.wsdbookstoreassign2.cart.service;

import kr.ac.jbnu.ksh.wsdbookstoreassign2.book.domain.Book;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.book.repository.BookRepository;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.cart.domain.Cart;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.cart.domain.CartItem;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.cart.dto.*;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.cart.repository.CartItemRepository;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.cart.repository.CartRepository;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.common.exception.ForbiddenException;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.common.exception.NotFoundException;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.order.service.CurrentUserResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final CurrentUserResolver currentUser;

    public CartResponse getMyCart(Authentication auth) {
        Long userId = currentUser.currentUserId(auth);
        Cart cart = cartRepository.findByUserIdAndDeletedAtIsNull(userId)
                .orElseGet(() -> cartRepository.save(Cart.builder()
                        .userId(userId)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()));
        return CartResponse.from(cart);
    }

    public CartResponse addItem(Authentication auth, CartItemAddRequest req) {
        Long userId = currentUser.currentUserId(auth);

        Cart cart = cartRepository.findByUserIdAndDeletedAtIsNull(userId)
                .orElseGet(() -> cartRepository.save(Cart.builder()
                        .userId(userId)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()));

        Book book = bookRepository.findById(req.getBookId())
                .orElseThrow(() -> new NotFoundException("Book not found. id=" + req.getBookId()));

        CartItem item = cartItemRepository.findByCartIdAndBookId(cart.getId(), book.getId())
                .orElse(null);

        if (item == null) {
            CartItem created = CartItem.builder()
                    .bookId(book.getId())
                    .unitPrice(book.getPrice())
                    .quantity(req.getQuantity())
                    .build();
            cart.addItem(created);
        } else {
            item.setQuantity(item.getQuantity() + req.getQuantity());
        }

        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);

        Cart reloaded = cartRepository.findByUserIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new NotFoundException("Cart not found after save."));
        return CartResponse.from(reloaded);
    }

    public CartResponse updateItem(Authentication auth, Long itemId, CartItemUpdateRequest req) {
        Long userId = currentUser.currentUserId(auth);

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("CartItem not found. id=" + itemId));

        Cart cart = item.getCart();
        if (!cart.getUserId().equals(userId)) throw new ForbiddenException("Not your cart item.");

        item.setQuantity(req.getQuantity());
        cart.setUpdatedAt(LocalDateTime.now());

        cartItemRepository.save(item);

        Cart reloaded = cartRepository.findByUserIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new NotFoundException("Cart not found after update."));
        return CartResponse.from(reloaded);
    }

    public CartResponse removeItem(Authentication auth, Long itemId) {
        Long userId = currentUser.currentUserId(auth);

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("CartItem not found. id=" + itemId));

        Cart cart = item.getCart();
        if (!cart.getUserId().equals(userId)) throw new ForbiddenException("Not your cart item.");

        cart.removeItem(item);
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);

        Cart reloaded = cartRepository.findByUserIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new NotFoundException("Cart not found after delete."));
        return CartResponse.from(reloaded);
    }

    public void clear(Authentication auth) {
        Long userId = currentUser.currentUserId(auth);

        Cart cart = cartRepository.findByUserIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new NotFoundException("Cart not found."));

        cart.getItems().clear();
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
    }
}
