package kr.ac.jbnu.ksh.wsdbookstoreassign2.review.dto;

import kr.ac.jbnu.ksh.wsdbookstoreassign2.review.domain.Review;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReviewResponse(
        Long id,
        Long userId,
        Long bookId,
        Integer rating,
        String content,
        Integer likesCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ReviewResponse from(Review r) {
        return ReviewResponse.builder()
                .id(r.getId())
                .userId(r.getUserId())
                .bookId(r.getBookId())
                .rating(r.getRating())
                .content(r.getContent())
                .likesCount(r.getLikesCount())
                .createdAt(r.getCreatedAt())
                .updatedAt(r.getUpdatedAt())
                .build();
    }
}
