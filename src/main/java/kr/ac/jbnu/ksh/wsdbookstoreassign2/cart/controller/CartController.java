package kr.ac.jbnu.ksh.wsdbookstoreassign2.cart.controller;

import jakarta.validation.Valid;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.cart.dto.*;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;


@Tag(name = "Carts", description = "장바구니 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    @Operation(summary = "내 장바구니 조회", description = "현재 로그인한 사용자의 장바구니와 아이템 목록을 조회합니다.")
    @GetMapping("/me")
    public CartResponse getMyCart(Authentication auth) {
        return cartService.getMyCart(auth);
    }

    @Operation(summary = "장바구니 담기", description = "장바구니에 도서와 수량을 추가합니다. 이미 존재하는 도서는 정책에 따라 수량을 증가시키거나 409를 반환합니다.")
    @PostMapping("/me/items")
    public CartResponse addItem(Authentication auth, @RequestBody @Valid CartItemAddRequest req) {
        return cartService.addItem(auth, req);
    }

    @Operation(summary = "장바구니 수량 변경", description = "장바구니 아이템의 수량을 변경합니다. 0 이하 수량 등 유효하지 않은 값이면 400을 반환합니다.")
    @PutMapping("/me/items/{itemId}")
    public CartResponse updateItem(Authentication auth, @PathVariable Long itemId, @RequestBody @Valid CartItemUpdateRequest req) {
        return cartService.updateItem(auth, itemId, req);
    }

    @Operation(summary = "장바구니 아이템 삭제", description = "장바구니에서 특정 아이템을 제거합니다.")
    @DeleteMapping("/me/items/{itemId}")
    public CartResponse removeItem(Authentication auth, @PathVariable Long itemId) {
        return cartService.removeItem(auth, itemId);
    }

    @Operation(summary = "장바구니 비우기", description = "현재 로그인한 사용자의 장바구니 아이템을 전체 삭제합니다.")
    @DeleteMapping("/me/items")
    public void clear(Authentication auth) {
        cartService.clear(auth);
    }
}
