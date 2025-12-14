package kr.ac.jbnu.ksh.wsdbookstoreassign2.user.repository;

import kr.ac.jbnu.ksh.wsdbookstoreassign2.user.domain.Role;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.user.domain.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {

    public static Specification<User> keyword(String keyword) {
        if (keyword == null || keyword.isBlank()) return null;
        String k = "%" + keyword.trim().toLowerCase() + "%";
        return (root, query, cb) -> cb.or(
                cb.like(cb.lower(root.get("email")), k),
                cb.like(cb.lower(root.get("name")), k)
        );
    }

    public static Specification<User> role(Role role) {
        if (role == null) return null;
        return (root, query, cb) -> cb.equal(root.get("role"), role);
    }

    public static Specification<User> notDeleted() {
        return (root, query, cb) -> cb.isNull(root.get("deletedAt"));
    }

    public static Specification<User> deleted() {
        return (root, query, cb) -> cb.isNotNull(root.get("deletedAt"));
    }
}
