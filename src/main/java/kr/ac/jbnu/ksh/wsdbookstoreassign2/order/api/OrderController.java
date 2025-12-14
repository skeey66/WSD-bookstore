package kr.ac.jbnu.ksh.wsdbookstoreassign2.order.api;

import jakarta.validation.Valid;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.order.domain.OrderStatus;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.order.dto.*;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@Tag(name = "Orders", description = "주문 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "주문 생성", description = "현재 로그인한 사용자가 주문을 생성합니다(장바구니 기반 또는 요청 아이템 기반). 재고/상태 등 논리 오류면 422/409를 반환할 수 있습니다.")
    @PostMapping
    public OrderResponse create(Authentication auth, @Valid @RequestBody OrderCreateRequest req) {
        return orderService.create(auth, req);
    }

    @Operation(summary = "주문 상세 조회", description = "주문 ID로 주문 상세(주문 아이템 포함)를 조회합니다. 본인 주문이 아니면 403을 반환합니다.")
    @GetMapping("/{id}")
    public OrderResponse get(Authentication auth, @PathVariable Long id) {
        return orderService.get(auth, id);
    }

    @Operation(summary = "주문 목록 조회", description = "주문 목록을 페이지네이션/정렬로 조회합니다.")
    @GetMapping
    public Page<OrderResponse> list(
            Authentication auth,
            @RequestParam(required = false) OrderStatus status,
            Pageable pageable
    ) {
        return orderService.list(auth, status, pageable);
    }

    @Operation(summary = "주문 취소", description = "주문을 취소합니다. 이미 처리 완료된 주문 등 상태 충돌 시 409를 반환합니다.")
    @PostMapping("/{id}/cancel")
    public OrderResponse cancel(Authentication auth, @PathVariable Long id) {
        return orderService.cancel(auth, id);
    }

    @Operation(summary = "주문 상태 변경", description = "주문 상태를 변경합니다")
    @PatchMapping("/{id}/status")
    public OrderResponse updateStatus(
            Authentication auth,
            @PathVariable Long id,
            @Valid @RequestBody OrderStatusUpdateRequest req
    ) {
        return orderService.updateStatus(auth, id, req.getStatus());
    }
}
