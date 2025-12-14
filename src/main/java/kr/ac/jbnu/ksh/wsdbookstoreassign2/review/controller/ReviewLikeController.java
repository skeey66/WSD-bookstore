package kr.ac.jbnu.ksh.wsdbookstoreassign2.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Reviews_Like", description = "좋아요한 리뷰")
@RestController
@RequiredArgsConstructor
public class ReviewLikeController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 좋아요 추가", description = "로그인한 사용자가 특정 리뷰에 좋아요를 추가합니다. 이미 좋아요한 경우 409(Conflict)를 반환할 수 있습니다.")
    @PostMapping("/reviews/{reviewId}/likes")
    public void like(@PathVariable Long reviewId, Authentication auth) {
        reviewService.like(auth, reviewId);
    }

    @Operation(summary = "리뷰 좋아요 취소", description = "로그인한 사용자가 특정 리뷰의 좋아요를 취소합니다. 좋아요가 없는 상태에서 취소하면 404 또는 409를 반환할 수 있습니다(정책에 따름).")
    @DeleteMapping("/reviews/{reviewId}/likes")
    public void unlike(@PathVariable Long reviewId, Authentication auth) {
        reviewService.unlike(auth, reviewId);
    }

    @Operation(summary = "리뷰 좋아요 개수 조회", description = "특정 리뷰에 등록된 좋아요의 총 개수를 조회합니다.")
    @GetMapping("/reviews/{reviewId}/likes/count")
    public long count(@PathVariable Long reviewId) {
        return reviewService.likeCount(reviewId);
    }
}
