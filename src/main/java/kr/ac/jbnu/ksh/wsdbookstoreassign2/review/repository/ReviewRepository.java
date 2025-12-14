package kr.ac.jbnu.ksh.wsdbookstoreassign2.review.repository;

import kr.ac.jbnu.ksh.wsdbookstoreassign2.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {

    Page<Review> findByBookIdAndDeletedAtIsNull(Long bookId, Pageable pageable);

    interface ReviewStatsProjection {
        long getCnt();
        Double getAvg();
    }

    @Query("""
        select count(r) as cnt, avg(r.rating) as avg
        from Review r
        where r.bookId = :bookId and r.deletedAt is null
    """)
    ReviewStatsProjection statsByBookId(@Param("bookId") Long bookId);
}
