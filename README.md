# 📚 Bookstore API (WSD Assignment 2)

온라인 서점 시스템을 위한 **RESTful API 서버**입니다.  
(Spring Boot + MySQL + Flyway + JWT Access/Refresh + RBAC + Swagger + JCloud 배포)

---

## 프로젝트 개요
본 프로젝트는 온라인 서점의 핵심 기능(도서/리뷰/장바구니/찜하기/주문)을 구현한 REST API 서버입니다.

### 문제 정의
- 도서 검색 및 상세 정보 제공
- 사용자 리뷰 및 평점 시스템
- 찜하기/장바구니/주문 관리
- 관리자(ADMIN)의 도서/주문 관리 및 운영 기능 제공

### 해결 방안
- **Spring Boot 기반 RESTful API**
- **JWT 인증(Access + Refresh) + 역할 기반 권한 관리(RBAC)**
- **Flyway로 데이터베이스 버전 관리 + 시드 데이터 구성**
- **Swagger(OpenAPI) 문서 제공**
- **JCloud 배포(systemd 서비스로 지속 구동) + Health check 제공**

---

## 주요 기능

### 사용자(USER) 기능
- ✅ 로그인/토큰 재발급/로그아웃 (JWT Access/Refresh)
- ✅ 도서 조회: 검색/정렬/페이지네이션
- ✅ 리뷰: 작성/수정/삭제
- ✅ 찜하기(Wishlist): 추가/삭제/조회
- ✅ 장바구니(Cart): 담기/수량변경/삭제/조회
- ✅ 주문(Order): 생성/조회/취소(상태 전이)

### 관리자(ADMIN) 기능
- ✅ 도서 관리: 등록/수정/삭제
- ✅ 관리자 전용 API: 권한 검증(예: `/admin/ping`)
- ✅ 주문 관리(관리자): 전체 조회/상세/상태 변경(구현 정책에 따름)

> 실제 상세 기능/정책은 Swagger 문서를 기준으로 합니다.

---

## 기술 스택

| 영역 | 기술 | 버전 |
|---|---|---|
| Language | Java | 25 |
| Framework | Spring Boot | 4.0.0 |
| Build Tool | Gradle | (프로젝트 설정) |
| Database | MySQL | 8.x |
| ORM | Spring Data JPA (Hibernate) | (기본) |
| Security | Spring Security + JWT | (구현) |
| Migration | Flyway | (사용) |
| Documentation | springdoc-openapi (Swagger UI) | (사용) |
| Monitoring | Spring Actuator | (사용) |
| Testing | JUnit 5 + MockMvc | (사용) |

---

## 실행 방법

### Prerequisites
- Java 25
- MySQL 8.x
- (선택) Git

### 1) 로컬 실행

#### 1. 데이터베이스 생성
```sql
CREATE DATABASE bookstore CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

#### 2. 환경변수 설정
`.env.example`를 참고해 환경변수를 설정합니다.

- macOS/Linux (bash)
```bash
export DB_URL="jdbc:mysql://127.0.0.1:3306/bookstore?serverTimezone=Asia/Seoul&characterEncoding=UTF-8"
export DB_USERNAME="bookstore_user"
export DB_PASSWORD="bookstore_password"
export JWT_SECRET="change_me_super_secret"
```

- Windows PowerShell
```powershell
$env:DB_URL="jdbc:mysql://127.0.0.1:3306/bookstore?serverTimezone=Asia/Seoul&characterEncoding=UTF-8"
$env:DB_USERNAME="bookstore_user"
$env:DB_PASSWORD="bookstore_password"
$env:JWT_SECRET="change_me_super_secret"
```

#### 3. 마이그레이션(Flyway) + 시드 데이터
```bash
./gradlew flywayMigrate
```

> ✅ 주의: 이미 적용된 마이그레이션 파일(V*__*.sql)을 수정하면 **checksum mismatch**가 발생할 수 있습니다.  
> 수정 대신 **새 버전(Vxx) 마이그레이션 추가**를 권장합니다.

#### 4. 서버 실행
```bash
./gradlew clean build
./gradlew bootRun

# 또는 jar 실행
java -jar build/libs/*.jar
```

#### 5. 실행 확인
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Health: `http://localhost:8080/actuator/health`

---

## 환경변수 설명

`.env.example` (예시)
```bash
DB_URL=jdbc:mysql://127.0.0.1:3306/bookstore?serverTimezone=Asia/Seoul&characterEncoding=UTF-8
DB_USERNAME=bookstore_user
DB_PASSWORD=bookstore_password
JWT_SECRET=change_me_super_secret
```

| 변수명 | 설명 |
|---|---|
| DB_URL | MySQL JDBC 접속 URL |
| DB_USERNAME | DB 사용자명 |
| DB_PASSWORD | DB 비밀번호 |
| JWT_SECRET | JWT 서명 키(비밀값) |

---

## 배포 주소

### Local
- Base URL: `http://localhost:8080`
- Swagger URL: `http://localhost:8080/swagger-ui/index.html`
- Health URL: `http://localhost:8080/actuator/health`

### Production (JCloud)
> JCloud는 포트 리다이렉션이 적용되어 **외부 포트**로 접속합니다.

- Base URL: `http://<JCLOUD_IP>:<EXTERNAL_PORT>`
- Swagger URL: `http://<JCLOUD_IP>:<EXTERNAL_PORT>/swagger-ui/index.html`
- Health URL: `http://<JCLOUD_IP>:<EXTERNAL_PORT>/actuator/health`

예시(외부 포트가 10224인 경우):
- `http://<JCLOUD_IP>:10224/swagger-ui/index.html`
- `http://<JCLOUD_IP>:10224/actuator/health`

---

## 인증 플로우

### 1) 로그인
`POST /auth/login`

- 성공 시 Access/Refresh 발급
- Refresh Token은 서버(DB)에 저장/관리

### 2) API 호출
```
Authorization: Bearer {accessToken}
```

### 3) 토큰 갱신
`POST /auth/refresh`

### 4) 로그아웃
`POST /auth/logout`  
- Refresh Token을 DB에서 폐기(무효화)

---

## 역할/권한표

> ✅ permitAll(인증 불필요) / 🔒 로그인 필요 / 👑 관리자 전용

| API | Method | Path | USER | ADMIN | 비고 |
|---|---:|---|:---:|:---:|---|
| Swagger | GET | `/swagger-ui/**` | ✅ | ✅ | 문서 |
| OpenAPI | GET | `/v3/api-docs/**` | ✅ | ✅ | 문서 |
| Health | GET | `/actuator/health` | ✅ | ✅ | 상태 확인 |
| Login | POST | `/auth/login` | ✅ | ✅ | 인증 불필요 |
| Refresh | POST | `/auth/refresh` | ✅ | ✅ | 인증 불필요(정책에 따름) |
| Logout | POST | `/auth/logout` | 🔒 | 🔒 | 로그인 필요 |
| Admin Ping | GET | `/admin/ping` | ❌ | ✅ | 관리자 전용 |
| Books | GET | `/books` | ✅/🔒 | ✅ | 조회 정책에 따름 |
| Books | POST/PUT/DELETE | `/books...` | ❌ | ✅ | 관리자 전용(정책) |
| Reviews | POST/PUT/DELETE | `/reviews...` | ✅ | ✅ | 로그인 필요 |
| Wishlist | * | `/wishlist/me...` | ✅ | ✅ | 내 계정 |
| Cart | * | `/cart/me...` | ✅ | ✅ | 내 계정 |
| Orders | * | `/orders...` | ✅ | ✅ | 내 계정 |
| Admin Orders | * | `/admin/orders...` | ❌ | ✅ | 관리자 전용 |

---

## 예제 계정
> 시드 데이터 또는 과제 제출용 테스트 계정 예시입니다. (필요 시 프로젝트 seed에 맞게 수정)

- USER: `user1@example.com / P@ssw0rd!`
- ADMIN: `admin@example.com / P@ssw0rd!` (ROLE_ADMIN)

---

## 엔드포인트 요약표
> 상세 API 명세는 Swagger를 기준으로 합니다.

| 도메인 | Method | URL | 설명 |
|---|---:|---|---|
| Auth | POST | `/auth/login` | 로그인 |
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
| Wishlist | GET | `/wishlist/me` | 내 위시리스트 |
| Wishlist | POST | `/wishlist/me/{bookId}` | 위시리스트 추가 |
| Wishlist | DELETE | `/wishlist/me/{bookId}` | 위시리스트 삭제 |
| Cart | GET | `/cart/me` | 내 장바구니 |
| Cart | POST | `/cart/me/items/{bookId}` | 장바구니 담기 |
| Cart | PATCH | `/cart/me/items/{bookId}` | 수량 변경 |
| Cart | DELETE | `/cart/me/items/{bookId}` | 삭제 |
| Order | POST | `/orders` | 주문 생성 |
| Order | GET | `/orders/me` | 내 주문 목록 |
| Order | GET | `/orders/me/{orderId}` | 내 주문 상세 |
| Order | PATCH | `/orders/me/{orderId}/cancel` | 주문 취소 |
| Admin | GET | `/admin/ping` | 관리자 확인 |
| Ops | GET | `/actuator/health` | Health check |

---

## API 응답 형식

### 성공 응답(예시)
```json
{
  "isSuccess": true,
  "message": "요청이 성공적으로 처리되었습니다",
  "code": null,
  "payload": {}
}
```

### 페이지네이션 응답(예시)
```json
{
  "isSuccess": true,
  "message": "조회 성공",
  "code": null,
  "payload": {
    "content": [],
    "pageable": { "pageNumber": 0, "pageSize": 20 },
    "totalElements": 100,
    "totalPages": 5
  }
}
```

### 에러 응답(예시)
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

## 에러 코드
> 프로젝트의 GlobalExceptionHandler 정책에 따라 코드/메시지는 달라질 수 있습니다.

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

## 성능/보안 고려사항

### 보안
- JWT 인증: Access/Refresh 토큰 기반
- 비밀번호 해싱: BCrypt
- 입력 검증: Bean Validation(@Valid 등) + 에러 응답 규격화
- 민감정보: DB/JWT 시크릿은 **환경변수로만 관리**

### 성능
- 검색/정렬/페이지네이션을 통한 조회 부하 완화
- 인덱스 권장
  - `books.isbn`(unique), FK 기반 조회 컬럼 인덱스
- (선택) 요청 제한/레이트리밋은 운영 환경에서 추가 적용 가능

---

## 테스트
```bash
# 전체 테스트 실행
./gradlew test
```

---

## 문서
- Swagger UI (Local): `http://localhost:8080/swagger-ui/index.html`
- Swagger UI (JCloud): `http://<JCLOUD_IP>:<EXTERNAL_PORT>/swagger-ui/index.html`

---

## 프로젝트 구조
```
wsd-bookstoreassign2/
├── src/
│   ├── main/
│   │   ├── java/kr/ac/jbnu/ksh/wsdbookstoreassign2/
│   │   │   ├── auth/          # JWT, login/refresh/logout
│   │   │   ├── user/
│   │   │   ├── book/
│   │   │   ├── review/
│   │   │   ├── cart/
│   │   │   ├── wishlist/
│   │   │   ├── order/
│   │   │   └── global/        # 예외/응답 규격 등
│   │   └── resources/
│   │       ├── db/migration/  # Flyway migrations (V*__*.sql)
│   │       └── application.yml
│   └── test/
├── build.gradle
├── gradlew / gradlew.bat
├── .env.example
└── README.md
```

---

## DB 연결 정보

### 로컬(MySQL)
- Host: `127.0.0.1`
- Port: `3306`
- Database: `bookstore`
- Username/Password: 환경변수(`DB_USERNAME`, `DB_PASSWORD`)

### JCloud(MySQL)
- 일반적으로 서버 내부에서 `127.0.0.1:3306` 로 접근하도록 구성

---

## 한계와 개선 계획

### 현재 한계
- 단일 인스턴스 기반(수평 확장/로드밸런서 미적용)
- 결제 시스템/알림/재고 동시성 제어는 과제 범위를 넘어 단순화

### 개선 계획
- 캐시/검색 최적화(예: Redis 캐시, 인덱스 튜닝) 확대
- 관측성 강화(구조화 로그, tracing/metrics)
- CI/CD 자동화(GitHub Actions) 및 무중단 배포 전략 적용
