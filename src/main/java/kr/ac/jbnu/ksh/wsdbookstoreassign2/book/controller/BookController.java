package kr.ac.jbnu.ksh.wsdbookstoreassign2.book.controller;

import jakarta.validation.Valid;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.book.dto.BookCreateRequest;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.book.dto.BookResponse;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.book.dto.BookUpdateRequest;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import java.math.BigDecimal;
import java.net.URI;

@Tag(name = "Books", description = "도서 API")
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // ADMIN만 생성(과제용 권장)
    @Operation(summary = "도서 등록", description = "관리자 권한으로 새 도서를 등록합니다. ISBN 중복 등 제약 위반 시 409를 반환합니다.")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookResponse> create(@RequestBody @Valid BookCreateRequest req) {
        BookResponse created = bookService.create(req);
        return ResponseEntity
                .created(URI.create("/books/" + created.id()))
                .body(created);
    }

    // 목록: page/size/sort + 검색/필터
    @Operation(summary = "도서 목록 조회", description = "도서 목록을 페이지네이션/정렬/검색 조건으로 조회합니다.")
    @GetMapping
    public ResponseEntity<Page<BookResponse>> list(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Integer yearFrom,
            @RequestParam(required = false) Integer yearTo,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(bookService.list(keyword, minPrice, maxPrice, yearFrom, yearTo, pageable));
    }

    @Operation(summary = "도서 상세 조회", description = "도서 ID로 도서 상세 정보를 조회합니다. 존재하지 않으면 404를 반환합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.get(id));
    }

    // ADMIN만 수정
    @Operation(summary = "도서 수정", description = "관리자 권한으로 도서 정보를 수정합니다. 존재하지 않으면 404를 반환합니다.")
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookResponse> update(@PathVariable Long id, @RequestBody @Valid BookUpdateRequest req) {
        return ResponseEntity.ok(bookService.update(id, req));
    }

    // ADMIN만 삭제
    @Operation(summary = "도서 삭제", description = "관리자 권한으로 도서를 삭제합니다(정책에 따라 soft delete 또는 hard delete).")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
