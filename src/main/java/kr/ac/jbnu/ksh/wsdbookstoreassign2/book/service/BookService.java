package kr.ac.jbnu.ksh.wsdbookstoreassign2.book.service;

import kr.ac.jbnu.ksh.wsdbookstoreassign2.book.domain.Book;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.book.dto.BookCreateRequest;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.book.dto.BookResponse;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.book.dto.BookUpdateRequest;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.book.repository.BookRepository;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.book.repository.BookSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.domain.Specification;


import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    public BookResponse create(BookCreateRequest req) {
        if (bookRepository.existsByIsbn(req.isbn())) {
            throw new IllegalStateException("DUPLICATE_RESOURCE");
        }

        Book b = new Book();
        b.setIsbn(req.isbn());
        b.setPublisher(req.publisher());
        b.setTitle(req.title());
        b.setSummary(req.summary());
        b.setPrice(req.price());
        b.setPublicationYear(req.publicationYear());

        try {
            return toResponse(bookRepository.save(b));
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("DUPLICATE_RESOURCE");
        }
    }

    @Transactional(readOnly = true)
    public BookResponse get(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("BOOK_NOT_FOUND"));
        return toResponse(book);
    }

    @Transactional(readOnly = true)
    public Page<BookResponse> list(
            String keyword,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Integer yearFrom,
            Integer yearTo,
            Pageable pageable
    ) {
        Specification<Book> spec = (root, query, cb) -> cb.conjunction(); // ✅ null-safe 시작

        if (keyword != null && !keyword.isBlank()) {
            spec = spec.and(BookSpecifications.keyword(keyword));
        }
        if (minPrice != null) {
            spec = spec.and(BookSpecifications.minPrice(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(BookSpecifications.maxPrice(maxPrice));
        }
        if (yearFrom != null) {
            spec = spec.and(BookSpecifications.yearFrom(yearFrom));
        }
        if (yearTo != null) {
            spec = spec.and(BookSpecifications.yearTo(yearTo));
        }

        return bookRepository.findAll(spec, pageable).map(this::toResponse);
    }

    public BookResponse update(Long id, BookUpdateRequest req) {
        Book b = bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("BOOK_NOT_FOUND"));

        if (req.isbn() != null && !req.isbn().isBlank() && !req.isbn().equals(b.getIsbn())) {
            if (bookRepository.existsByIsbn(req.isbn())) {
                throw new IllegalStateException("DUPLICATE_RESOURCE");
            }
            b.setIsbn(req.isbn());
        }
        if (req.publisher() != null) b.setPublisher(req.publisher());
        if (req.title() != null) b.setTitle(req.title());
        if (req.summary() != null) b.setSummary(req.summary());
        if (req.price() != null) b.setPrice(req.price());
        if (req.publicationYear() != null) b.setPublicationYear(req.publicationYear());

        try {
            return toResponse(bookRepository.save(b));
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("DUPLICATE_RESOURCE");
        }
    }

    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new NoSuchElementException("BOOK_NOT_FOUND");
        }
        bookRepository.deleteById(id);
    }

    private BookResponse toResponse(Book b) {
        return new BookResponse(
                b.getId(),
                b.getIsbn(),
                b.getPublisher(),
                b.getTitle(),
                b.getSummary(),
                b.getPrice(),
                b.getPublicationYear(),
                b.getCreatedAt(),
                b.getUpdatedAt()
        );
    }
}
