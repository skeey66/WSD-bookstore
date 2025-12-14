package kr.ac.jbnu.ksh.wsdbookstoreassign2.review.service;

import kr.ac.jbnu.ksh.wsdbookstoreassign2.book.repository.BookRepository;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.common.exception.ConflictException;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.common.exception.ForbiddenException;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.common.exception.NotFoundException;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.order.service.CurrentUserResolver;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.review.domain.*;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.review.dto.*;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.review.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final BookRepository bookRepository;
    private final CurrentUserResolver currentUser;

    public ReviewResponse create(Authentication auth, Long bookId, ReviewCreateRequest req) {
        Long userId = currentUser.currentUserId(auth);

        if (!bookRepository.existsById(bookId)) {
            throw new NotFoundException("Book not found. id=" + bookId);
        }

        Review review = Review.builder()
                .userId(userId)
                .bookId(bookId)
                .rating(req.getRating())
                .content(req.getContent())
                .likesCount(0)
                .build();

        return ReviewResponse.from(reviewRepository.save(review));
    }

    @Transactional(readOnly = true)
    public ReviewResponse get(Authentication auth, Long reviewId) {
        Review r = reviewRepository.findById(reviewId)
                .filter(x -> x.getDeletedAt() == null)
                .orElseThrow(() -> new NotFoundException("Review not found. id=" + reviewId));
        return ReviewResponse.from(r);
    }

    @Transactional(readOnly = true)
    public Page<ReviewResponse> listByBook(Long bookId, Pageable pageable) {
        if (!bookRepository.existsById(bookId)) {
            throw new NotFoundException("Book not found. id=" + bookId);
        }
        return reviewRepository.findByBookIdAndDeletedAtIsNull(bookId, pageable).map(ReviewResponse::from);
    }

    @Transactional(readOnly = true)
    public Page<ReviewResponse> search(Long bookId, String keyword, Integer minRating, Integer maxRating, Pageable pageable) {
        Specification<Review> spec = Specification.where(ReviewSpecifications.notDeleted())
                .and(ReviewSpecifications.bookIdEq(bookId))
                .and(ReviewSpecifications.keywordLike(keyword))
                .and(ReviewSpecifications.ratingGte(minRating))
                .and(ReviewSpecifications.ratingLte(maxRating));

        return reviewRepository.findAll(spec, pageable).map(ReviewResponse::from);
    }

    public ReviewResponse update(Authentication auth, Long reviewId, ReviewUpdateRequest req) {
        Review r = reviewRepository.findById(reviewId)
                .filter(x -> x.getDeletedAt() == null)
                .orElseThrow(() -> new NotFoundException("Review not found. id=" + reviewId));

        enforceOwnerOrAdmin(auth, r);

        r.setRating(req.getRating());
        r.setContent(req.getContent());
        return ReviewResponse.from(r);
    }

    public void delete(Authentication auth, Long reviewId) {
        Review r = reviewRepository.findById(reviewId)
                .filter(x -> x.getDeletedAt() == null)
                .orElseThrow(() -> new NotFoundException("Review not found. id=" + reviewId));

        enforceOwnerOrAdmin(auth, r);

        r.softDelete();
    }

    public void like(Authentication auth, Long reviewId) {
        Long userId = currentUser.currentUserId(auth);

        Review r = reviewRepository.findById(reviewId)
                .filter(x -> x.getDeletedAt() == null)
                .orElseThrow(() -> new NotFoundException("Review not found. id=" + reviewId));

        ReviewLikeId id = new ReviewLikeId(reviewId, userId);
        if (reviewLikeRepository.existsById(id)) {
            throw new ConflictException("Already liked. reviewId=" + reviewId);
        }

        reviewLikeRepository.save(ReviewLike.builder().id(id).build());
        r.setLikesCount(r.getLikesCount() + 1);
    }

    public void unlike(Authentication auth, Long reviewId) {
        Long userId = currentUser.currentUserId(auth);

        Review r = reviewRepository.findById(reviewId)
                .filter(x -> x.getDeletedAt() == null)
                .orElseThrow(() -> new NotFoundException("Review not found. id=" + reviewId));

        ReviewLikeId id = new ReviewLikeId(reviewId, userId);
        ReviewLike like = reviewLikeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Like not found. reviewId=" + reviewId));

        reviewLikeRepository.delete(like);
        r.setLikesCount(Math.max(0, r.getLikesCount() - 1));
    }

    @Transactional(readOnly = true)
    public long likeCount(Long reviewId) {
        // review 자체가 존재하는지 체크(없으면 404)
        reviewRepository.findById(reviewId)
                .filter(x -> x.getDeletedAt() == null)
                .orElseThrow(() -> new NotFoundException("Review not found. id=" + reviewId));

        return reviewLikeRepository.countByIdReviewId(reviewId);
    }

    @Transactional(readOnly = true)
    public ReviewStatsResponse stats(Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new NotFoundException("Book not found. id=" + bookId);
        }
        var p = reviewRepository.statsByBookId(bookId);
        long cnt = p == null ? 0 : p.getCnt();
        double avg = (p == null || p.getAvg() == null) ? 0.0 : p.getAvg();
        return new ReviewStatsResponse(bookId, cnt, avg);
    }

    private void enforceOwnerOrAdmin(Authentication auth, Review review) {
        if (currentUser.isAdmin(auth)) return;
        Long me = currentUser.currentUserId(auth);
        if (!review.getUserId().equals(me)) throw new ForbiddenException("Not your review.");
    }
}
