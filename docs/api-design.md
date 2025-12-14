# API Design (Assignment 1 반영/수정사항 요약)

본 문서는 **과제 1(API 설계)**를 기반으로 과제 2 구현 과정에서 확정된 API 규칙/변경사항을 정리한 문서입니다.  
(실제 최종 명세는 Swagger UI를 **Source of Truth**로 둡니다.)

---

## 1. 공통 규칙

### 1) Base URL
- Local: `http://localhost:8080`
- JCloud: `http://<JCLOUD_IP>:<EXTERNAL_PORT>`

> 본 프로젝트는 별도 `/api/v1` 프리픽스를 강제하지 않으며, 실제 경로는 Swagger 기준으로 확인합니다.

### 2) 인증 헤더
- Access Token 사용
```
Authorization: Bearer <ACCESS_TOKEN>
```

### 3) 응답 포맷(권장/구현 기준)
- 성공/실패 공통 포맷(프로젝트 GlobalExceptionHandler 및 공통 Response Wrapper 기준)

**성공 예시**
```json
{
  "isSuccess": true,
  "message": "OK",
  "code": null,
  "payload": {}
}
```

**에러 예시**
```json
{
  "timestamp": "2025-12-14T07:14:08",
  "path": "/books/999",
  "status": 404,
  "code": "RESOURCE_NOT_FOUND",
  "message": "요청한 리소스를 찾을 수 없습니다",
  "details": {}
}
```

### 4) 페이지네이션/정렬/검색(Books 등 목록 API)
- 페이지네이션 파라미터(예)
  - `page` (0-base)
  - `size`
- 정렬 파라미터(예)
  - `sort` (`title,asc` 형태 또는 프로젝트 정책)
- 검색 파라미터(예)
  - `q` / `title` / `author` / `publisher` / `category` 등
- 구현은 Spring Data(Pageable + Specification) 기반

> 실제 파라미터 이름/정렬 키는 Swagger와 Controller/DTO를 기준으로 합니다.

---

## 2. 인증(Auth) API

### 2.1 로그인
- `POST /auth/login`
- Request (예)
```json
{
  "email": "user1@example.com",
  "password": "P@ssw0rd!"
}
```
- Response (예)
```json
{
  "isSuccess": true,
  "payload": {
    "accessToken": "<JWT>",
    "refreshToken": "<JWT>"
  }
}
```

### 2.2 토큰 재발급
- `POST /auth/refresh`
- Refresh Token으로 Access Token 재발급

### 2.3 로그아웃
- `POST /auth/logout`
- 서버측 Refresh Token을 DB에서 폐기하여 무효화

---

## 3. 리소스 API (요약)

> 아래는 과제 설계의 “구성/의도”를 요약한 것이며, 실제 경로는 Swagger 기준입니다.

### 3.1 Book
- 도서 CRUD + 검색/정렬/페이지네이션
- (과제2 구현 반영) Create/Update 요청 DTO는 Validation 적용(@NotBlank 등)

**중요 변경사항(과제 진행 중 확정)**
- `BookCreateRequest`/`BookUpdateRequest`에서 `publisher`가 필수 필드(@NotBlank)로 사용됨  
  - Postman 테스트 중 `publisher` 누락 시 400 Validation 에러 발생 → 스펙 확정

### 3.2 Review
- 리뷰 CRUD
- (정책) 본인 리뷰만 수정/삭제 가능하도록 소유자 검증 적용 가능

### 3.3 Wishlist
- “내 계정” 기반 Sub-resource 형태
- 예) `POST /wishlist/me/{bookId}`, `GET /wishlist/me`, `DELETE /wishlist/me/{bookId}`

### 3.4 Cart
- “내 계정” 기반 Sub-resource 형태
- 예) `GET /cart/me`, `POST /cart/me/items/{bookId}`, `PATCH ...`, `DELETE ...`

### 3.5 Order
- 주문 생성/조회/취소(상태 전이)
- 관리자 주문 조회/상태 변경 API 제공(과제 요구사항 충족 목적)

---

## 4. 권한 정책 (RBAC)
- **permitAll**
  - Swagger: `/swagger-ui/**`, `/v3/api-docs/**`
  - Health: `/actuator/health`
  - Auth: `/auth/**` (프로젝트 설정에 따름)
- **authenticated**
  - 그 외 대부분 API
- **ADMIN 전용**
  - `/admin/**` 또는 도서 등록/수정/삭제 등 운영 기능

테스트 기준:
- 토큰 없음/유효하지 않음 → 401
- 토큰은 있으나 권한 부족 → 403

---

## 5. 에러 처리 정책
- Validation 실패 → 400 (필드별 오류 detail 포함 가능)
- 리소스 없음 → 404
- 중복(예: ISBN unique) → 409
- 기타 예외 → 500 (표준 포맷 유지)

---

## 6. Postman 테스트 구성(요약)
- USER 토큰 변수: `userAccessToken`, `userRefreshToken`
- ADMIN 토큰 변수: `adminAccessToken`, `adminRefreshToken`
- Create Book 성공 시 `bookId` 환경변수 저장 → Wishlist/Cart 등 후속 요청에 사용

---

## 7. Source of Truth
- 최종 API 명세: Swagger UI  
  - `/swagger-ui/index.html`
  - `/v3/api-docs`
