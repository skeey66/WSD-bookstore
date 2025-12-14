package kr.ac.jbnu.ksh.wsdbookstoreassign2.wishlist.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "wishlist")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Wishlist {

    @EmbeddedId
    private WishlistId id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
