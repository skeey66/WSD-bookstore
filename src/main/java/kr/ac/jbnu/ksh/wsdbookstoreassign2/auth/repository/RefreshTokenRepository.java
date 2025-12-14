package kr.ac.jbnu.ksh.wsdbookstoreassign2.auth.repository;

import kr.ac.jbnu.ksh.wsdbookstoreassign2.auth.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
}
