package kr.ac.jbnu.ksh.wsdbookstoreassign2.admin.service;

import kr.ac.jbnu.ksh.wsdbookstoreassign2.admin.dto.AdminUserResponse;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.common.exception.NotFoundException;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.user.domain.Role;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.user.domain.User;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.user.repository.UserRepository;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.user.repository.UserSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<AdminUserResponse> listUsers(String keyword, Role role, boolean includeDeleted, Pageable pageable) {
        Specification<User> spec = (root, query, cb) -> cb.conjunction();

        if (keyword != null && !keyword.isBlank()) spec = spec.and(UserSpecifications.keyword(keyword));
        if (role != null) spec = spec.and(UserSpecifications.role(role));
        if (!includeDeleted) spec = spec.and(UserSpecifications.notDeleted());

        return userRepository.findAll(spec, pageable).map(AdminUserResponse::from);
    }



    @Transactional
    public AdminUserResponse updateRole(Long id, Role role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("USER_NOT_FOUND"));

        user.setRole(role);
        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    @Transactional
    public AdminUserResponse deactivate(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("USER_NOT_FOUND"));

        if (user.getDeletedAt() == null) {
            user.setDeletedAt(OffsetDateTime.now());
        }

        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    private AdminUserResponse toResponse(User u) {
        return new AdminUserResponse(
                u.getId(),
                u.getEmail(),
                u.getName(),
                u.getRole(),
                u.getCreatedAt(),
                u.getUpdatedAt(),
                u.getDeletedAt()
        );
    }
}
