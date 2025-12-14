package kr.ac.jbnu.ksh.wsdbookstoreassign2.admin.controller;

import jakarta.validation.Valid;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.admin.dto.AdminUserResponse;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.admin.dto.AdminUserRoleUpdateRequest;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.admin.service.AdminUserService;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.user.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import java.util.Map;

@Tag(name = "Admin", description = "관리자 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminUserService adminUserService;

    @Operation(summary = "관리자 핑", description = "관리자 권한/인증 동작 확인을 위한 테스트 엔드포인트입니다.")
    @GetMapping("/ping")
    public Map<String, String> ping() {
        return Map.of("ok", "admin");
    }

    /**
     * GET /admin/users?keyword=&role=&includeDeleted=&page=&size=&sort=
     */
    @Operation(summary = "회원 목록 조회", description = "관리자 권한으로 전체 회원 목록을 페이지네이션/정렬로 조회합니다.")
    @GetMapping("/users")
    public Page<AdminUserResponse> listUsers(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Role role,
            @RequestParam(required = false, defaultValue = "false") Boolean includeDeleted,
            Pageable pageable
    ) {
        return adminUserService.listUsers(keyword, role, includeDeleted, pageable);
    }

    @Operation(summary = "회원 권한 변경", description = "관리자 권한으로 특정 회원의 Role을 변경합니다(예: USER ↔ ADMIN).")
    @PatchMapping("/users/{id}/role")
    public AdminUserResponse updateRole(
            @PathVariable Long id,
            @Valid @RequestBody AdminUserRoleUpdateRequest req
    ) {
        return adminUserService.updateRole(id, req.role());
    }

    @Operation(summary = "회원 비활성화", description = "관리자 권한으로 특정 회원을 비활성화 처리합니다.")
    @PatchMapping("/users/{id}/deactivate")
    public AdminUserResponse deactivate(@PathVariable Long id) {
        return adminUserService.deactivate(id);
    }
}
