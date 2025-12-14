# Architecture (계층/의존성/모듈 구조 개요)

본 문서는 프로젝트의 **계층 구조, 모듈 구성, 인증 흐름, 배포/운영 구조**를 간단히 정리합니다.

---

## 1. 전체 구조 요약

### 기술 구성
- Java 25 + Spring Boot 4.0.0
- Spring MVC + Spring Data JPA
- Spring Security + JWT(Access/Refresh)
- Flyway (DB Migration)
- springdoc-openapi (Swagger)
- Actuator (Health)

---

## 2. 계층(레이어) 구조

일반적인 호출 흐름은 다음과 같습니다.

```
[Controller] -> [Service] -> [Repository] -> [DB]
                 |
                 -> (도메인 규칙/검증, 트랜잭션)
```

### Controller
- HTTP 요청/응답 담당
- DTO(Request/Response) 매핑
- Validation(@Valid) 수행

### Service
- 비즈니스 로직의 중심
- @Transactional 기반 트랜잭션 처리
- 중복/권한/상태 전이 등 도메인 규칙 적용

### Repository
- Spring Data JPA 기반 CRUD/쿼리
- Book 검색/필터링은 Specification + Pageable로 구현

---

## 3. Security 아키텍처(JWT + RBAC)

### 인증 흐름
- `OncePerRequestFilter`에서 Authorization 헤더의 Bearer 토큰 파싱
- 유효한 토큰이면 SecurityContext에 Authentication 주입
- role(예: `ROLE_USER`, `ROLE_ADMIN`)을 부여하여 RBAC 적용

### 인가 정책(요약)
- permitAll: Swagger/Actuator/인증(Auth) 엔드포인트
- authenticated: 나머지 API
- ADMIN 전용: `/admin/**` 및 운영 API

### 토큰 전략
- Access Token: 요청 인증용(Stateless)
- Refresh Token: DB 저장/검증 (로그아웃 시 폐기하여 서버측 무효화)

---

## 4. 예외 처리 / 응답 규격화
- GlobalExceptionHandler(@ControllerAdvice)로 예외를 표준 포맷으로 변환
- Validation/NotFound/Conflict 등 케이스별 status + code + message 통일

---

## 5. 모듈(패키지) 구조

프로젝트는 도메인 중심으로 패키지를 분리합니다(예):

```
kr.ac.jbnu.ksh.wsdbookstoreassign2
├── auth/           # login/refresh/logout, jwt 필터/유틸
├── user/           # 사용자 도메인/서비스
├── book/           # 도서 CRUD + 검색/정렬/페이지
├── review/         # 리뷰 CRUD
├── cart/           # 장바구니
├── wishlist/       # 위시리스트
├── order/          # 주문/상태 전이
└── global/         # 공통 응답/예외/설정
```

DB 마이그레이션:
```
src/main/resources/db/migration/V*__*.sql
```

---

## 6. 배포/운영 구조 (JCloud)

### 프로세스
- build 후 JAR 배포
- systemd 서비스로 등록하여 재부팅/재시작에도 지속 구동

예시:
- 서비스명: `wsd-bookstoreassign2`
- 실행: `/home/ubuntu/app/app.jar`
- 내부 포트: 8080
- 외부 접속: JCloud 포트 리다이렉션(예: 외부 10224 → 내부 8080)

### 헬스체크
- `/actuator/health`에서 `UP` 확인

---

## 7. 운영시 주의사항
- Flyway 적용된 migration은 수정하지 않기(Checksum mismatch 방지)
- 비밀값(DB/JWT secret)은 환경변수로만 주입하고 Git에 커밋 금지
