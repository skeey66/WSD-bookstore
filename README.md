WSD Bookstore Assignment 2 — Spring Boot API (JWT + RBAC + Flyway)
1) 프로젝트 개요 (문제 정의 / 주요 기능)
문제 정의

과제 1에서 설계한 DB/REST API를 기반으로 도서 쇼핑/관리 서비스 API 서버를 구현하고, 인증/인가(JWT + RBAC), 문서화(Swagger), 테스트(Postman/자동화), **배포(JCloud)**까지 완성한다.

주요 기능 목록

인증/인가

JWT Access + Refresh 토큰 기반 Stateless 인증

Refresh Token DB 저장 + 로그아웃 시 폐기

ROLE_USER / ROLE_ADMIN 권한 분리

리소스 API

Book CRUD + 검색/정렬/페이지네이션

Review CRUD

Cart / Wishlist (내 계정 기반 Sub-resource)

Order(주문) 생성/조회/상태변경 + Admin 관리 API

운영/품질

Flyway DB 마이그레이션 + seed 데이터

GlobalExceptionHandler로 에러 응답 규격화

Swagger(OpenAPI) 문서 제공

Actuator Health check 제공

2) 실행 방법
2.1 로컬 실행
1) 의존성 설치/빌드
# 프로젝트 루트
./gradlew clean build

2) DB 준비 (MySQL)

MySQL에 DB 생성 (예시):

CREATE DATABASE bookstore CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

3) 환경변수 설정

.env.example 참고해서 환경변수 주입 (Windows PowerShell 예시):

$env:DB_URL="jdbc:mysql://127.0.0.1:3306/bookstore?serverTimezone=Asia/Seoul&characterEncoding=UTF-8"
$env:DB_USERNAME="bookstore_user"
$env:DB_PASSWORD="bookstore_password"
$env:JWT_SECRET="change_me_super_secret"

4) 마이그레이션(Flyway) + 시드 데이터

프로젝트 구성에 따라 seed가 Flyway에 포함되어 있거나(권장: V*__seed.sql), 애플리케이션 실행 시 주입될 수 있음.

# Flyway 마이그레이션 실행 (Gradle 플러그인 사용)
./gradlew flywayMigrate

5) 서버 실행
./gradlew bootRun
# 또는 빌드된 jar 실행
java -jar build/libs/*.jar

6) 실행 확인

Swagger UI: http://localhost:8080/swagger-ui/index.html

OpenAPI JSON: http://localhost:8080/v3/api-docs

Health: http://localhost:8080/actuator/health

3) 환경변수 설명 (.env.example 매칭)
.env.example
# Database
DB_URL=jdbc:mysql://127.0.0.1:3306/bookstore?serverTimezone=Asia/Seoul&characterEncoding=UTF-8
DB_USERNAME=bookstore_user
DB_PASSWORD=bookstore_password

# JWT
JWT_SECRET=change_me_super_secret
JWT_ACCESS_EXPIRES_MIN=30
JWT_REFRESH_EXPIRES_DAYS=7

변수 설명

DB_URL : JDBC URL (MySQL)

DB_USERNAME / DB_PASSWORD : DB 계정/비밀번호

JWT_SECRET : JWT 서명용 비밀키

JWT_ACCESS_EXPIRES_MIN : Access Token 만료(분)

JWT_REFRESH_EXPIRES_DAYS : Refresh Token 만료(일)

4) 배포 주소 (JCloud)

포트 리다이렉션이 적용되어 **외부 포트(<EXTERNAL_PORT>)**로 접속한다.

Base URL: http://<JCLOUD_IP>:<EXTERNAL_PORT>

Swagger URL: http://<JCLOUD_IP>:<EXTERNAL_PORT>/swagger-ui/index.html

Health URL: http://<JCLOUD_IP>:<EXTERNAL_PORT>/actuator/health

5) 인증 플로우 설명 (JWT Access + Refresh)
5.1 로그인 → 토큰 발급

POST /auth/login

성공 시 Access/Refresh 발급

Refresh Token은 서버(DB)에 저장

5.2 API 호출

Authorization 헤더에 Access Token 사용

Authorization: Bearer <ACCESS_TOKEN>

5.3 Access 만료 시 재발급

POST /auth/refresh

Refresh Token 검증 후 새로운 Access 발급

5.4 로그아웃

POST /auth/logout

해당 Refresh Token을 DB에서 폐기(무효화)

6) 역할/권한표 (ROLE_USER / ROLE_ADMIN)

✅ permitAll: 누구나 접근 가능
🔒 authenticated: 로그인 필요
👑 adminOnly: 관리자만

구분	엔드포인트	메서드	권한	설명
Public	/swagger-ui/**, /v3/api-docs/**	GET	✅	API 문서
Public	/actuator/health	GET	✅	Health Check
Auth	/auth/login	POST	✅	로그인
Auth	/auth/refresh	POST	✅	Access 재발급
Auth	/auth/logout	POST	🔒	로그아웃(Refresh 폐기)
Admin	/admin/ping	GET	👑	관리자 권한 확인
Book	/books	GET	✅/🔒(정책에 따름)	목록/검색/정렬/페이지
Book	/books/{id}	GET	✅/🔒(정책에 따름)	단건 조회
Book	/books	POST	👑	도서 등록
Book	/books/{id}	PUT	👑	도서 수정
Book	/books/{id}	DELETE	👑	도서 삭제
Review	/reviews	POST	🔒	리뷰 작성
Review	/reviews	GET	✅/🔒(정책에 따름)	리뷰 목록
Review	/reviews/{id}	PUT/DELETE	🔒	리뷰 수정/삭제(소유자 검증)
Cart	/cart/me	GET	🔒	내 장바구니 조회
Cart	/cart/me/items/{bookId}	POST/PATCH/DELETE	🔒	장바구니 아이템 조작
Wishlist	/wishlist/me	GET	🔒	내 위시리스트 조회
Wishlist	/wishlist/me/{bookId}	POST/DELETE	🔒	위시리스트 추가/삭제
Order	/orders	POST	🔒	주문 생성
Order	/orders/me	GET	🔒	내 주문 목록
Order	/orders/me/{orderId}	GET	🔒	내 주문 상세
Order	/orders/me/{orderId}/cancel	PATCH	🔒	주문 취소
Admin Order	/admin/orders	GET	👑	전체 주문 조회
Admin Order	/admin/orders/{orderId}	GET	👑	주문 상세 조회
Admin Order	/admin/orders/{orderId}/status	PATCH	👑	주문 상태 변경
7) 예제 계정

제출용 기본 계정(요구사항 템플릿 그대로)

USER: user1@example.com / P@ssw0rd!

ADMIN: admin@example.com / P@ssw0rd!

⚠️ ADMIN 계정은 도서 등록/수정/삭제, 관리자 API 실행에 사용

8) DB 연결 정보 (테스트용)

개발/테스트 환경 기준. 운영 환경에서는 보안상 별도 계정/권한 관리 필요.

Host: 127.0.0.1

Port: 3306

DB Name: bookstore

User: bookstore_user

권한 범위(권장):

개발/테스트: bookstore DB에 대한 SELECT/INSERT/UPDATE/DELETE + (초기) CREATE/ALTER 가능

운영: 마이그레이션 계정/애플리케이션 계정 분리 권장

9) 엔드포인트 요약표 (URL · 메서드 · 설명)
영역	Method	URL	설명
Auth	POST	/auth/login	로그인 (토큰 발급)
Auth	POST	/auth/refresh	Access 재발급
Auth	POST	/auth/logout	로그아웃(Refresh 폐기)
Book	GET	/books	목록/검색/정렬/페이지
Book	GET	/books/{id}	단건 조회
Book	POST	/books	도서 등록(ADMIN)
Book	PUT	/books/{id}	도서 수정(ADMIN)
Book	DELETE	/books/{id}	도서 삭제(ADMIN)
Review	POST	/reviews	리뷰 작성
Review	GET	/reviews	리뷰 목록
Review	PUT	/reviews/{id}	리뷰 수정
Review	DELETE	/reviews/{id}	리뷰 삭제
Cart	GET	/cart/me	내 장바구니
Cart	POST	/cart/me/items/{bookId}	장바구니 담기
Cart	PATCH	/cart/me/items/{bookId}	수량 변경
Cart	DELETE	/cart/me/items/{bookId}	삭제
Wishlist	GET	/wishlist/me	내 위시리스트
Wishlist	POST	/wishlist/me/{bookId}	추가
Wishlist	DELETE	/wishlist/me/{bookId}	삭제
Order	POST	/orders	주문 생성
Order	GET	/orders/me	내 주문 목록
Order	GET	/orders/me/{orderId}	내 주문 상세
Order	PATCH	/orders/me/{orderId}/cancel	주문 취소
Admin	GET	/admin/ping	관리자 확인
Admin	GET	/admin/orders	전체 주문 조회
Admin	GET	/admin/orders/{orderId}	주문 상세
Admin	PATCH	/admin/orders/{orderId}/status	주문 상태 변경
Ops	GET	/actuator/health	Health Check
Docs	GET	/swagger-ui/index.html	Swagger UI
10) 성능/보안 고려사항
보안

비밀번호: BCrypt로 해시 저장

인증: Authorization Bearer JWT, 서버는 Stateless

Refresh Token: DB 저장 + 로그아웃 시 폐기

권한 분리: Spring Security + Method Security로 ADMIN/USER 정책 분리

입력 검증: Bean Validation(@NotBlank 등) + 에러 응답 규격화

민감정보 보호: DB/JWT 시크릿은 환경변수로만 관리

성능

페이지네이션/정렬로 목록 조회 부하 완화

DB 인덱스 권장

books.isbn (unique)

orders.user_id, reviews.user_id, cart_items.cart_id, wishlist.user_id 등 FK 기반 조회 인덱스

(선택) 레이트리밋

/auth/login, /auth/refresh에 IP/계정 기반 제한 적용 권장

11) 한계와 개선 계획
한계

주문/결제/배송 등 실제 결제 시스템 연동은 과제 범위를 넘어 단순 상태 전이로 구성

대규모 트래픽을 고려한 캐시/분산락/비동기 이벤트 처리까지는 미적용

개선 계획

Redis 기반 캐시(Top-N 도서, 검색 결과 캐싱) 및 Refresh Token 저장소 분리

레이트리밋/계정 잠금 정책 강화(Brute force 방어)

Observability 강화(요청 로그 구조화, TraceId, Metrics 대시보드)

테스트 확대(권한/상태전이/동시성 케이스)

12) (선택) 배포 운영 메모 (systemd)
sudo systemctl restart wsd-bookstoreassign2
sudo systemctl status wsd-bookstoreassign2 --no-pager -l
journalctl -u wsd-bookstoreassign2 -n 200 --no-pager
