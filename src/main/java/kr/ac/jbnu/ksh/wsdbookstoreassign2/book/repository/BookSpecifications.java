package kr.ac.jbnu.ksh.wsdbookstoreassign2.book.repository;

import kr.ac.jbnu.ksh.wsdbookstoreassign2.book.domain.Book;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class BookSpecifications {

    public static Specification<Book> keyword(String keyword) {
        if (keyword == null || keyword.isBlank()) return null;
        String k = "%" + keyword.trim().toLowerCase() + "%";
        return (root, query, cb) -> cb.or(
                cb.like(cb.lower(root.get("title")), k),
                cb.like(cb.lower(root.get("publisher")), k),
                cb.like(cb.lower(root.get("isbn")), k)
        );
    }

    public static Specification<Book> minPrice(BigDecimal minPrice) {
        if (minPrice == null) return null;
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Book> maxPrice(BigDecimal maxPrice) {
        if (maxPrice == null) return null;
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<Book> yearFrom(Integer from) {
        if (from == null) return null;
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("publicationYear"), from);
    }

    public static Specification<Book> yearTo(Integer to) {
        if (to == null) return null;
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("publicationYear"), to);
    }
}
