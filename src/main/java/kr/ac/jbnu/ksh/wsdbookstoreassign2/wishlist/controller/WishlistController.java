package kr.ac.jbnu.ksh.wsdbookstoreassign2.wishlist.controller;

import kr.ac.jbnu.ksh.wsdbookstoreassign2.wishlist.dto.WishlistItemResponse;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.wishlist.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;
import java.util.Map;

@Tag(name = "Wishlist", description = "위시리스트 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    // 찜 추가
    @Operation(summary = "찜 추가", description = "책 ID를 기반으로 찜 추가합니다.")
    @PostMapping("/me/{bookId}")
    public void add(Authentication auth, @PathVariable Long bookId) {
        wishlistService.add(auth, bookId);
    }

    // 찜 해제
    @Operation(summary = "찜 삭제", description = "현재 로그인한 사용자의 찜 목록에서 도서를 제거합니다. 존재하지 않으면 404를 반환합니다.")
    @DeleteMapping("/me/{bookId}")
    public void remove(Authentication auth, @PathVariable Long bookId) {
        wishlistService.remove(auth, bookId);
    }

    // 내 찜 목록
    @Operation(summary = "내 찜 목록 조회", description = "현재 로그인한 사용자의 찜(위시리스트) 목록을 조회합니다.")
    @GetMapping("/me")
    public List<WishlistItemResponse> list(Authentication auth) {
        return wishlistService.list(auth);
    }

    // 내 찜 개수
    @Operation(summary = "내 찜 개수 조회", description = "현재 로그인한 사용자의 찜(위시리스트) 개수를 조회합니다.")
    @GetMapping("/me/count")
    public Map<String, Long> count(Authentication auth) {
        return Map.of("count", wishlistService.count(auth));
    }

    // 특정 책 찜 여부 확인
    @Operation(summary = "특정 책 찜 여부 확인", description = "책 ID를 기반으로 찜 여부를 확인합니다.")
    @GetMapping("/me/{bookId}")
    public Map<String, Boolean> isWished(Authentication auth, @PathVariable Long bookId) {
        return Map.of("wished", wishlistService.isWished(auth, bookId));
    }
}
