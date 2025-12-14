package kr.ac.jbnu.ksh.wsdbookstoreassign2.user.service;

import kr.ac.jbnu.ksh.wsdbookstoreassign2.user.domain.User;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.user.dto.UserCreateRequest;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.user.dto.UserResponse;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse signUp(UserCreateRequest req) {
        if (userRepository.existsByEmail(req.email())) {
            throw new IllegalStateException("DUPLICATE_EMAIL");
        }

        User u = new User();
        u.setEmail(req.email());
        u.setName(req.name());
        u.setGender(req.gender());
        u.setBirthDate(req.birthDate());
        u.setAddress(req.address());
        u.setPhoneNumber(req.phoneNumber());
        u.setPasswordHash(passwordEncoder.encode(req.password())); // 해시 저장

        User saved = userRepository.save(u);

        return new UserResponse(saved.getId(), saved.getEmail(), saved.getName(), saved.getRole());
    }

    @Transactional(readOnly = true)
    public UserResponse getById(Long id) {
        User u = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("USER_NOT_FOUND"));

        return new UserResponse(u.getId(), u.getEmail(), u.getName(), u.getRole());
    }
}
