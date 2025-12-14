package kr.ac.jbnu.ksh.wsdbookstoreassign2.wishlist.service;

import kr.ac.jbnu.ksh.wsdbookstoreassign2.book.domain.Book;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.book.repository.BookRepository;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.common.exception.ConflictException;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.common.exception.NotFoundException;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.order.service.CurrentUserResolver;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.wishlist.domain.Wishlist;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.wishlist.domain.WishlistId;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.wishlist.dto.WishlistItemResponse;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final BookRepository bookRepository;
    private final CurrentUserResolver currentUser;

    public void add(Authentication auth, Long bookId) {
        Long userId = currentUser.currentUserId(auth);

        // book 존재 검증
        bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book not found. id=" + bookId));

        WishlistId id = new WishlistId(userId, bookId);
        if (wishlistRepository.existsById(id)) {
            throw new ConflictException("Already wished. bookId=" + bookId);
        }

        wishlistRepository.save(Wishlist.builder()
                .id(id)
                .createdAt(LocalDateTime.now())
                .build());
    }

    public void remove(Authentication auth, Long bookId) {
        Long userId = currentUser.currentUserId(auth);

        WishlistId id = new WishlistId(userId, bookId);
        if (!wishlistRepository.existsById(id)) {
            throw new NotFoundException("Wishlist item not found. bookId=" + bookId);
        }
        wishlistRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean isWished(Authentication auth, Long bookId) {
        Long userId = currentUser.currentUserId(auth);
        return wishlistRepository.existsById(new WishlistId(userId, bookId));
    }

    @Transactional(readOnly = true)
    public long count(Authentication auth) {
        Long userId = currentUser.currentUserId(auth);
        return wishlistRepository.countByIdUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<WishlistItemResponse> list(Authentication auth) {
        Long userId = currentUser.currentUserId(auth);

        List<Wishlist> rows = wishlistRepository.findAllByIdUserIdOrderByCreatedAtDesc(userId);
        if (rows.isEmpty()) return List.of();

        List<Long> bookIds = rows.stream().map(r -> r.getId().getBookId()).toList();
        Map<Long, Book> bookMap = bookRepository.findAllById(bookIds).stream()
                .collect(Collectors.toMap(Book::getId, b -> b));

        // 응답: 찜 시간(createdAt) + 책 정보
        List<WishlistItemResponse> result = new ArrayList<>();
        for (Wishlist w : rows) {
            Long bid = w.getId().getBookId();
            Book b = bookMap.get(bid);
            if (b == null) continue; // 이론상 없어야 함

            result.add(new WishlistItemResponse(
                    b.getId(),
                    b.getTitle(),
                    b.getPublisher(),
                    b.getPrice(),
                    w.getCreatedAt()
            ));
        }
        return result;
    }
}
