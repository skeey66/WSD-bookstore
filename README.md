# WSD Bookstore Assignment 2 — Spring Boot API (JWT + RBAC + Flyway)

## 1) 프로젝트 개요 (문제 정의 / 주요 기능)
### 문제 정의
과제 1에서 설계한 DB/REST API를 기반으로 **도서 쇼핑/관리 서비스 API 서버**를 구현하고, **인증/인가(JWT + RBAC)**, **문서화(Swagger)**, **테스트(Postman/자동화)**, **배포(JCloud)**까지 완성한다.

### 주요 기능 목록
- **인증/인가**
  - JWT **Access + Refresh** 토큰 기반 Stateless 인증
  - Refresh Token **DB 저장 + 로그아웃 시 폐기**
  - **ROLE_USER / ROLE_ADMIN** 권한 분리
- **리소스 API**
  - Book CRUD + **검색/정렬/페이지네이션**
  - Review CRUD
  - Cart / Wishlist (내 계정 기반 Sub-resource)
  - Order(주문) 생성/조회/취소 + Admin 관리 API(정책에 따름)
- **운영/품질**
  - Flyway DB 마이그레이션 + seed 데이터
  - GlobalExceptionHandler로 **에러 응답 규격화**
  - Swagger(OpenAPI) 문서 제공
  - Actuator Health check 제공
  - JCloud 배포(systemd 서비스로 지속 구동)

---

## 2) 기술 스택
- **Language**: Java **25**
- **Framework**: Spring Boot **4.0.0**
- **Build Tool**: Gradle (Wrapper 포함)
- **Database**: MySQL (로컬/JCloud)
- **ORM**: Spring Data JPA (Hibernate)
- **Migration**: Flyway
- **Security**: Spring Security + JWT
- **Documentation**: springdoc-openapi (Swagger UI)
- **Monitoring**: Spring Boot Actuator
- **Testing**: JUnit 5 + MockMvc

---

## 3) 실행 방법

### 3.1 로컬 실행

#### 0) Prerequisites
- Java **25**
- MySQL (예: 8.x 권장)
- (선택) Git

#### 1) 의존성 설치/빌드
```bash
# 프로젝트 루트
./gradlew clean build
```

#### 2) DB 준비 (MySQL)
MySQL에 DB 생성 (예시):
```sql
CREATE DATABASE bookstore CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

#### 3) 환경변수 설정
`.env.example` 참고해서 환경변수 주입

- macOS / Linux (bash):
```bash
export DB_URL="jdbc:mysql://127.0.0.1:3306/bookstore?serverTimezone=Asia/Seoul&characterEncoding=UTF-8"
export DB_USERNAME="bookstore_user"
export DB_PASSWORD="bookstore_password"
export JWT_SECRET="change_me_super_secret"
```

- Windows PowerShell:
```powershell
$env:DB_URL="jdbc:mysql://127.0.0.1:3306/bookstore?serverTimezone=Asia/Seoul&characterEncoding=UTF-8"
$env:DB_USERNAME="bookstore_user"
$env:DB_PASSWORD="bookstore_password"
$env:JWT_SECRET="change_me_super_secret"
```

#### 4) 마이그레이션(Flyway) + 시드 데이터
```bash
# Flyway 마이그레이션 실행 (Gradle 플러그인 사용)
./gradlew flywayMigrate
```

#### 5) 서버 실행
```bash
./gradlew bootRun

# 또는 빌드된 jar 실행
java -jar build/libs/*.jar
```

#### 6) 실행 확인
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Health: `http://localhost:8080/actuator/health`

---

## 4) 환경변수 설명 (.env.example 매칭)

### `.env.example`
```bash
# Database
DB_URL=jdbc:mysql://127.0.0.1:3306/bookstore?serverTimezone=Asia/Seoul&characterEncoding=UTF-8
DB_USERNAME=bookstore_user
DB_PASSWORD=bookstore_password

# JWT
JWT_SECRET=change_me_super_secret
JWT_ACCESS_EXPIRES_MIN=30
JWT_REFRESH_EXPIRES_DAYS=7
```

### 변수 설명
- `DB_URL` : JDBC URL (MySQL)
- `DB_USERNAME` / `DB_PASSWORD` : DB 계정/비밀번호
- `JWT_SECRET` : JWT 서명용 비밀키
- `JWT_ACCESS_EXPIRES_MIN` : Access Token 만료(분) *(프로젝트 설정에 따라 선택)*
- `JWT_REFRESH_EXPIRES_DAYS` : Refresh Token 만료(일) *(프로젝트 설정에 따라 선택)*

---

## 5) 배포 주소 (JCloud)
> JCloud는 포트 리다이렉션이 적용되어 **외부 포트(`<EXTERNAL_PORT>`)**로 접속한다.

- Base URL: `http://113.198.66.68:10224`
- Swagger URL: `http://113.198.66.68:10224/swagger-ui/index.html`
- Health URL: `http://113.198.66.68:10224/actuator/health`

---

## 6) 인증 플로우 설명 (JWT Access + Refresh)

### 6.1 로그인 → 토큰 발급
1) `POST /auth/login`
- 성공 시 Access/Refresh 발급
- Refresh Token은 서버(DB)에 저장

### 6.2 API 호출
- Authorization 헤더에 Access Token 사용
  - `Authorization: Bearer <ACCESS_TOKEN>`

### 6.3 Access 만료 시 재발급
1) `POST /auth/refresh`
- Refresh Token 검증 후 새로운 Access 발급

### 6.4 로그아웃
1) `POST /auth/logout`
- 해당 Refresh Token을 **DB에서 폐기(무효화)**

---

## 7) 역할/권한표 (ROLE_USER / ROLE_ADMIN)

> ✅ permitAll: 누구나 접근 가능  
> 🔒 authenticated: 로그인 필요  
> 👑 adminOnly: 관리자만

| 구분 | 엔드포인트 | 메서드 | 권한 | 설명 |
|---|---|---:|---|---|
| Public | `/swagger-ui/**`, `/v3/api-docs/**` | GET | ✅ | API 문서 |
| Public | `/actuator/health` | GET | ✅ | Health Check |
| Auth | `/auth/login` | POST | ✅ | 로그인 |
| Auth | `/auth/refresh` | POST | ✅ | Access 재발급 *(정책에 따름)* |
| Auth | `/auth/logout` | POST | 🔒 | 로그아웃(Refresh 폐기) |
| Admin | `/admin/ping` | GET | 👑 | 관리자 권한 확인 |
| Book | `/books` | GET | ✅/🔒(정책에 따름) | 목록/검색/정렬/페이지 |
| Book | `/books/{id}` | GET | ✅/🔒(정책에 따름) | 단건 조회 |
| Book | `/books` | POST | 👑 | 도서 등록 |
| Book | `/books/{id}` | PUT | 👑 | 도서 수정 |
| Book | `/books/{id}` | DELETE | 👑 | 도서 삭제 |
| Review | `/reviews` | POST | 🔒 | 리뷰 작성 |
| Review | `/reviews` | GET | ✅/🔒(정책에 따름) | 리뷰 목록 |
| Review | `/reviews/{id}` | PUT/DELETE | 🔒 | 리뷰 수정/삭제(소유자 검증) |
| Cart | `/cart/me` | GET | 🔒 | 내 장바구니 조회 |
| Cart | `/cart/me/items/{bookId}` | POST/PATCH/DELETE | 🔒 | 장바구니 아이템 조작 |
| Wishlist | `/wishlist/me` | GET | 🔒 | 내 위시리스트 조회 |
| Wishlist | `/wishlist/me/{bookId}` | POST/DELETE | 🔒 | 위시리스트 추가/삭제 |
| Order | `/orders` | POST | 🔒 | 주문 생성 |
| Order | `/orders/me` | GET | 🔒 | 내 주문 목록 |
| Order | `/orders/me/{orderId}` | GET | 🔒 | 내 주문 상세 |
| Order | `/orders/me/{orderId}/cancel` | PATCH | 🔒 | 주문 취소 |
| Admin Order | `/admin/orders` | GET | 👑 | 전체 주문 조회 |
| Admin Order | `/admin/orders/{orderId}` | GET | 👑 | 주문 상세 조회 |
| Admin Order | `/admin/orders/{orderId}/status` | PATCH | 👑 | 주문 상태 변경 |

---

## 8) 예제 계정
> 제출용 테스트 계정 예시 (seed 데이터 구성에 맞게 수정 가능)
- USER: user1@example.com / (비밀번호는 Classroom 제출 파일 참고)
- ADMIN: admin@example.com / (비밀번호는 Classroom 제출 파일 참고)

  - ⚠️ ADMIN 계정은 **도서 등록/수정/삭제, 관리자 API** 실행에 사용

---

## 9) DB 연결 정보(테스트용)
> 개발/테스트 환경 기준. 운영 환경에서는 보안상 별도 계정/권한 관리 권장.

- Host: `127.0.0.1`
- Port: `3306`
- DB Name: `bookstore`
- User: `bookstore_user`
- 권한 범위(권장):
  - 개발/테스트: `bookstore` DB에 대한 `SELECT/INSERT/UPDATE/DELETE` + (초기) `CREATE/ALTER` 가능
  - 운영: 마이그레이션 계정/애플리케이션 계정 분리 권장

---

## 10) 엔드포인트 요약표 (URL · 메서드 · 설명)

| 영역 | Method | URL | 설명 |
|---|---:|---|---|
| Auth | POST | `/auth/login` | 로그인 (토큰 발급) |
| Auth | POST | `/auth/refresh` | Access 재발급 |
| Auth | POST | `/auth/logout` | 로그아웃(Refresh 폐기) |
| Book | GET | `/books` | 목록/검색/정렬/페이지 |
| Book | GET | `/books/{id}` | 단건 조회 |
| Book | POST | `/books` | 도서 등록(ADMIN) |
| Book | PUT | `/books/{id}` | 도서 수정(ADMIN) |
| Book | DELETE | `/books/{id}` | 도서 삭제(ADMIN) |
| Review | POST | `/reviews` | 리뷰 작성 |
| Review | GET | `/reviews` | 리뷰 목록 |
| Review | PUT | `/reviews/{id}` | 리뷰 수정 |
| Review | DELETE | `/reviews/{id}` | 리뷰 삭제 |
| Cart | GET | `/cart/me` | 내 장바구니 |
| Cart | POST | `/cart/me/items/{bookId}` | 장바구니 담기 |
| Cart | PATCH | `/cart/me/items/{bookId}` | 수량 변경 |
| Cart | DELETE | `/cart/me/items/{bookId}` | 삭제 |
| Wishlist | GET | `/wishlist/me` | 내 위시리스트 |
| Wishlist | POST | `/wishlist/me/{bookId}` | 추가 |
| Wishlist | DELETE | `/wishlist/me/{bookId}` | 삭제 |
| Order | POST | `/orders` | 주문 생성 |
| Order | GET | `/orders/me` | 내 주문 목록 |
| Order | GET | `/orders/me/{orderId}` | 내 주문 상세 |
| Order | PATCH | `/orders/me/{orderId}/cancel` | 주문 취소 |
| Admin | GET | `/admin/ping` | 관리자 확인 |
| Admin | GET | `/admin/orders` | 전체 주문 조회 |
| Admin | GET | `/admin/orders/{orderId}` | 주문 상세 |
| Admin | PATCH | `/admin/orders/{orderId}/status` | 주문 상태 변경 |
| Ops | GET | `/actuator/health` | Health Check |
| Docs | GET | `/swagger-ui/index.html` | Swagger UI |

---

## 11) API 응답 형식(예시)

### 성공 응답
```json
{
  "isSuccess": true,
  "message": "요청이 성공적으로 처리되었습니다",
  "code": null,
  "payload": {}
}
```

### 페이지네이션 응답
```json
{
  "isSuccess": true,
  "message": "조회 성공",
  "code": null,
  "payload": {
    "content": [],
    "pageable": {
      "pageNumber": 0,
      "pageSize": 20
    },
    "totalElements": 100,
    "totalPages": 5
  }
}
```

### 에러 응답
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

---

## 12) 에러 코드(예시)
> GlobalExceptionHandler 정책에 따라 코드/메시지는 달라질 수 있습니다.

| HTTP | Code | Description |
|---:|---|---|
| 400 | VALIDATION_ERROR | 입력값 검증 실패 |
| 400 | BAD_REQUEST | 잘못된 요청 |
| 401 | UNAUTHORIZED | 인증 필요 |
| 401 | TOKEN_EXPIRED | 토큰 만료 |
| 403 | FORBIDDEN | 권한 없음 |
| 404 | RESOURCE_NOT_FOUND | 리소스 없음 |
| 405 | METHOD_NOT_ALLOWED | 메서드 미지원 |
| 409 | DUPLICATE_RESOURCE | 중복 리소스 |
| 409 | STATE_CONFLICT | 상태 충돌 |
| 500 | INTERNAL_SERVER_ERROR | 서버 오류 |

---

## 13) 성능/보안 고려사항
### 보안
- 비밀번호: **BCrypt**로 해시 저장
- 인증: Authorization Bearer JWT, 서버는 Stateless
- Refresh Token: **DB 저장 + 로그아웃 시 폐기**
- 권한 분리: Spring Security + Method Security로 ADMIN/USER 정책 분리
- 입력 검증: Bean Validation(@NotBlank 등) + 에러 응답 규격화
- 민감정보 보호: DB/JWT 시크릿은 **환경변수로만 관리**

### 성능
- 페이지네이션/정렬로 목록 조회 부하 완화
- DB 인덱스 권장
  - `books.isbn` (unique)
  - `orders.user_id`, `reviews.user_id`, `cart_items.cart_id`, `wishlist.user_id` 등 FK 기반 조회 인덱스
- (선택) `/auth/login`, `/auth/refresh` 레이트리밋 적용 권장(운영 환경)

---

## 14) 테스트
```bash
# 전체 테스트 실행
./gradlew test
```

---

## 15) 한계와 개선 계획
### 한계
- 주문/결제/배송 등 실제 결제 시스템 연동은 과제 범위를 넘어 단순 상태 전이로 구성
- 단일 인스턴스 운영(수평 확장/로드밸런서 미적용)
- 대규모 트래픽 고려 캐시/비동기 이벤트 처리까지는 미적용

### 개선 계획
- Redis 캐시(Top-N 도서, 검색 결과 캐싱) 및 토큰 저장소 분리(정책에 따라)
- Observability 강화(구조화 로그, TraceId, Metrics)
- CI/CD 자동화(GitHub Actions) 및 무중단 배포 전략 적용

---

## 16) (선택) 배포 운영 메모 (systemd)
```bash
sudo systemctl restart wsd-bookstoreassign2
sudo systemctl status wsd-bookstoreassign2 --no-pager -l
journalctl -u wsd-bookstoreassign2 -n 200 --no-pager
```
