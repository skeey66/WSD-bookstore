package kr.ac.jbnu.ksh.wsdbookstoreassign2.review.repository;

import kr.ac.jbnu.ksh.wsdbookstoreassign2.review.domain.ReviewLike;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.review.domain.ReviewLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, ReviewLikeId> {
    long countByIdReviewId(Long reviewId);
}
