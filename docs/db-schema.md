# DB Schema (ERD/스키마 정의)

본 문서는 과제 1에서 설계한 ERD를 기반으로 과제 2 구현에서 사용되는 **주요 테이블/관계/제약**을 요약합니다.  
최종 스키마는 `src/main/resources/db/migration`의 Flyway SQL을 기준으로 합니다.

> 이미지/ERD 파일이 있다면 `db-schema.png`를 함께 첨부/참조하세요.

---

## 1. 스키마 관리 방식 (Flyway)
- 마이그레이션 경로: `src/main/resources/db/migration`
- 파일 규칙: `V{번호}__{설명}.sql`
- ✅ 이미 적용된 마이그레이션 파일을 수정하면 **checksum mismatch** 발생 가능  
  → 수정 대신 **새로운 V 파일 추가** 권장

---

## 2. 주요 엔티티(테이블) 요약

> 아래는 “대표 구성”이며, 실제 컬럼/제약은 Flyway SQL과 엔티티 정의를 따릅니다.

### 2.1 users
- 사용자 계정 정보
- 주요 컬럼(예)
  - `id` (PK)
  - `email` (UNIQUE)
  - `password` (BCrypt 해시)
  - `role` (USER/ADMIN)
  - `created_at`, `updated_at`

### 2.2 refresh_tokens
- Refresh Token 저장/폐기(로그아웃 시 삭제 또는 만료 처리)
- 주요 컬럼(예)
  - `id` (PK)
  - `user_id` (FK → users.id)
  - `token` (UNIQUE 또는 인덱스)
  - `expires_at`

### 2.3 books
- 도서 정보
- 주요 컬럼(예)
  - `id` (PK)
  - `title`
  - `publisher` (Create/Update 시 필수 검증)
  - `isbn` (UNIQUE 권장)
  - `price`
  - `created_at`, `updated_at`

### 2.4 reviews
- 도서 리뷰
- 관계
  - `reviews.user_id` → `users.id`
  - `reviews.book_id` → `books.id`

### 2.5 wishlist (또는 favorites)
- 사용자-도서 관심 목록
- 관계
  - `wishlist.user_id` → `users.id`
  - `wishlist.book_id` → `books.id`
- 제약
  - `(user_id, book_id)` UNIQUE 권장(중복 찜 방지)

### 2.6 carts / cart_items
- 장바구니(카트)와 아이템
- 관계
  - `carts.user_id` → `users.id`
  - `cart_items.cart_id` → `carts.id`
  - `cart_items.book_id` → `books.id`
- 제약
  - `(cart_id, book_id)` UNIQUE 권장(동일 도서 중복 아이템 방지)

### 2.7 orders / order_items
- 주문과 주문 아이템
- 관계
  - `orders.user_id` → `users.id`
  - `order_items.order_id` → `orders.id`
  - `order_items.book_id` → `books.id`
- 상태
  - `orders.status` (예: CREATED/PAID/CANCELED/SHIPPED/DELIVERED 등 정책에 따름)
- (선택) soft delete 컬럼
  - `deleted_at` 사용 가능(프로젝트 정책에 따름)

---

## 3. 인덱스/제약 권장 사항
- `users.email` UNIQUE
- `books.isbn` UNIQUE
- FK 컬럼 인덱스
  - `reviews(book_id, user_id)`
  - `orders(user_id)`
  - `cart_items(cart_id)`, `wishlist(user_id)`
- 교차 테이블 중복 방지 UNIQUE
  - `wishlist(user_id, book_id)`
  - `cart_items(cart_id, book_id)`

---

## 4. 데이터(seed)
- 과제 요구사항에 따라 테스트/검증을 위한 시드 데이터를 충분히 구성합니다.
- 시드는 Flyway 마이그레이션 SQL에 포함하거나(예: seed용 V파일),
  애플리케이션 초기화 로직으로 주입할 수 있습니다(프로젝트 정책에 따름).

---

## 5. 참고
- 최종 스키마: `db/migration` SQL + JPA 엔티티
- ERD 이미지: `docs/db-schema.png` (있다면 첨부)
