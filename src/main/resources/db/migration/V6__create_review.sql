CREATE TABLE IF NOT EXISTS review (
                                      review_id BIGINT NOT NULL AUTO_INCREMENT,
                                      user_id BIGINT NOT NULL,
                                      book_id BIGINT NOT NULL,
                                      rating INT NOT NULL,
                                      content VARCHAR(2000) NOT NULL,
    likes_count INT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    deleted_at DATETIME NULL,
    PRIMARY KEY (review_id),
    INDEX idx_review_user_id (user_id),
    INDEX idx_review_book_id (book_id)
    );


ALTER TABLE review
    ADD CONSTRAINT fk_review_user_id FOREIGN KEY (user_id) REFERENCES `user`(user_id);

ALTER TABLE review
    ADD CONSTRAINT fk_review_book_id FOREIGN KEY (book_id) REFERENCES book(book_id);
