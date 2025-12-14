package kr.ac.jbnu.ksh.wsdbookstoreassign2.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.auth.jwt.JwtTokenProvider;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.book.domain.Book;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.book.repository.BookRepository;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.user.domain.Role;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.user.domain.User;
import kr.ac.jbnu.ksh.wsdbookstoreassign2.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public abstract class ApiTestSupport {

    @Autowired protected MockMvc mockMvc;


    @Autowired protected UserRepository userRepository;
    @Autowired protected BookRepository bookRepository;

    @Autowired protected PasswordEncoder passwordEncoder;
    @Autowired protected JwtTokenProvider jwtTokenProvider;

    protected User admin;
    protected User user;
    protected Book book;
    protected final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @BeforeEach
    void setUpBaseData() {
        // @Transactional이라 각 테스트 끝나면 롤백됨
        admin = createUser("admin@admin.com", Role.ADMIN);
        user = createUser("user1@example.com", Role.USER);
        book = createBook("ISBN-0001");
    }

    protected User createUser(String email, Role role) {
        User u = new User();
        u.setEmail(email);
        u.setName(role == Role.ADMIN ? "Admin" : "User");
        u.setPasswordHash(passwordEncoder.encode("P@ssw0rd!"));
        u.setGender("M");
        u.setBirthDate(LocalDate.of(2000, 1, 1));
        u.setAddress("addr");
        u.setPhoneNumber("010-0000-0000");
        u.setRole(role);
        u.setCreatedAt(OffsetDateTime.now());
        u.setUpdatedAt(OffsetDateTime.now());
        return userRepository.save(u);
    }

    protected Book createBook(String isbn) {
        Book b = new Book();
        b.setIsbn(isbn);
        b.setPublisher("Pub");
        b.setTitle("Title " + isbn);
        b.setSummary("Summary");
        b.setPrice(new BigDecimal("15000"));
        b.setPublicationYear(2024);
        b.setCreatedAt(OffsetDateTime.now());
        b.setUpdatedAt(OffsetDateTime.now());
        return bookRepository.save(b);
    }

    protected String bearerFor(User u) {
        String role = "ROLE_" + u.getRole().name();
        String token = jwtTokenProvider.createAccessToken(u.getId(), u.getEmail(), role);
        return "Bearer " + token;
    }
}
