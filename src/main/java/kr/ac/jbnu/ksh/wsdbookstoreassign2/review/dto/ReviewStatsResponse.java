package kr.ac.jbnu.ksh.wsdbookstoreassign2.review.dto;

public record ReviewStatsResponse(
        Long bookId,
        long reviewCount,
        double avgRating
) {}
