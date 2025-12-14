package kr.ac.jbnu.ksh.wsdbookstoreassign2.review.controller;

import jakarta.validation.Valid;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.review.dto.*;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@Tag(name = "Reviews", description = "리뷰 API")
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 작성 (서브리소스)
    @Operation(summary = "리뷰 작성", description = "특정 도서에 대한 리뷰를 작성합니다.")
    @PostMapping("/books/{bookId}/reviews")
    public ReviewResponse create(@PathVariable Long bookId,
                                 @Valid @RequestBody ReviewCreateRequest req,
                                 Authentication auth) {
        return reviewService.create(auth, bookId, req);
    }

    // 책별 리뷰 목록
    @Operation(summary = "도서 리뷰 목록 조회", description = "특정 도서의 리뷰 목록을 페이지네이션/정렬로 조회합니다.")
    @GetMapping("/books/{bookId}/reviews")
    public Page<ReviewResponse> listByBook(@PathVariable Long bookId,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "20") int size,
                                           @RequestParam(required = false) String sort) {
        Pageable pageable = pageable(page, size, sort);
        return reviewService.listByBook(bookId, pageable);
    }

    // 리뷰 검색/필터(전체)
    @Operation(summary = "리뷰 조회", description = "생성된 리뷰들을 조회합니다.")
    @GetMapping("/reviews")
    public Page<ReviewResponse> search(@RequestParam(required = false) Long bookId,
                                       @RequestParam(required = false) String keyword,
                                       @RequestParam(required = false) Integer minRating,
                                       @RequestParam(required = false) Integer maxRating,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "20") int size,
                                       @RequestParam(required = false) String sort) {
        Pageable pageable = pageable(page, size, sort);
        return reviewService.search(bookId, keyword, minRating, maxRating, pageable);
    }

    // 단건
    @Operation(summary = "리뷰 상세 조회", description = "리뷰 ID로 리뷰 상세를 조회합니다.")
    @GetMapping("/reviews/{reviewId}")
    public ReviewResponse get(@PathVariable Long reviewId, Authentication auth) {
        return reviewService.get(auth, reviewId);
    }

    // 수정
    @Operation(summary = "리뷰 완전 수정", description = "리뷰 ID로 리뷰를 완전 수정합니다.")
    @PutMapping("/reviews/{reviewId}")
    public ReviewResponse update(@PathVariable Long reviewId,
                                 @Valid @RequestBody ReviewUpdateRequest req,
                                 Authentication auth) {
        return reviewService.update(auth, reviewId, req);
    }

    // 삭제(soft delete)
    @Operation(summary = "리뷰 삭제", description = "리뷰 ID로 리뷰를 삭제합니다.")
    @DeleteMapping("/reviews/{reviewId}")
    public void delete(@PathVariable Long reviewId, Authentication auth) {
        reviewService.delete(auth, reviewId);
    }

    // stats
    @Operation(summary = "리뷰 상태", description = "책 ID로 해당 책의 리뷰 통계를 조회합니다.")
    @GetMapping("/books/{bookId}/reviews/stats")
    public ReviewStatsResponse stats(@PathVariable Long bookId) {
        return reviewService.stats(bookId);
    }

    @Operation(summary = "리뷰 일부 수정", description = "리뷰 ID로 리뷰 일부를 수정합니다.")
    @PatchMapping("/reviews/{reviewId}")
    public ReviewResponse updatePatch(@PathVariable Long reviewId,
                                      @Valid @RequestBody ReviewUpdateRequest req,
                                      Authentication auth) {
        return reviewService.update(auth, reviewId, req);
    }


    private Pageable pageable(int page, int size, String sort) {
        if (sort == null || sort.isBlank()) return PageRequest.of(page, size);
        // sort="createdAt,desc" 형태 지원
        String[] parts = sort.split(",");
        if (parts.length == 2) {
            return PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(parts[1]), parts[0]));
        }
        return PageRequest.of(page, size, Sort.by(sort));
    }
}
