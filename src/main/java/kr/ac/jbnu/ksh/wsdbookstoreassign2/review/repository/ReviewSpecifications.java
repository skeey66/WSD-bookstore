package kr.ac.jbnu.ksh.wsdbookstoreassign2.review.repository;

import kr.ac.jbnu.ksh.wsdbookstoreassign2.review.domain.Review;
import org.springframework.data.jpa.domain.Specification;

public class ReviewSpecifications {

    public static Specification<Review> notDeleted() {
        return (root, q, cb) -> cb.isNull(root.get("deletedAt"));
    }

    public static Specification<Review> bookIdEq(Long bookId) {
        return (root, q, cb) -> bookId == null ? cb.conjunction() : cb.equal(root.get("bookId"), bookId);
    }

    public static Specification<Review> ratingGte(Integer min) {
        return (root, q, cb) -> min == null ? cb.conjunction() : cb.greaterThanOrEqualTo(root.get("rating"), min);
    }

    public static Specification<Review> ratingLte(Integer max) {
        return (root, q, cb) -> max == null ? cb.conjunction() : cb.lessThanOrEqualTo(root.get("rating"), max);
    }

    public static Specification<Review> keywordLike(String keyword) {
        return (root, q, cb) -> {
            if (keyword == null || keyword.isBlank()) return cb.conjunction();
            String like = "%" + keyword.trim() + "%";
            return cb.like(root.get("content"), like);
        };
    }
}
