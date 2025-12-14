package kr.ac.jbnu.ksh.wsdbookstoreassign2.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "`user`")// MySQL 예약어 이슈 방지용
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 20)
    private String gender; // 일단 String으로(나중에 ENUM 가능)

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private String address;

    @Column(name = "phone_number", nullable = false, length = 30)
    private String phoneNumber;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role = Role.USER;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;


    @PrePersist
    void onCreate() {

        var now = OffsetDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }


    @PreUpdate
    void onUpdate() {

        this.updatedAt = OffsetDateTime.now();
    }
}
