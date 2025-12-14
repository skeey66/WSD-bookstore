package kr.ac.jbnu.ksh.wsdbookstoreassign2.user.controller;

import kr.ac.jbnu.ksh.wsdbookstoreassign2.user.dto.UserCreateRequest;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.user.dto.UserResponse;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;


@Tag(name = "Users", description = "사용자 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "새 사용자 계정을 생성합니다. 이메일 중복 등 제약 조건 위반 시 409를 반환합니다.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse signUp(@Valid @RequestBody UserCreateRequest req) {
        return userService.signUp(req);
    }

    @Operation(summary = "회원 조회", description = "회원 ID로 회원을 조회합니다.")
    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) {
        return userService.getById(id);
    }

}

