CREATE TABLE IF NOT EXISTS review_likes (
                                            review_id BIGINT NOT NULL,
                                            user_id BIGINT NOT NULL,
                                            created_at DATETIME NOT NULL,
                                            PRIMARY KEY (review_id, user_id),
    INDEX idx_review_likes_user_id (user_id),
    CONSTRAINT fk_review_likes_review_id FOREIGN KEY (review_id) REFERENCES review(review_id),
    CONSTRAINT fk_review_likes_user_id FOREIGN KEY (user_id) REFERENCES `user`(user_id)
    );
