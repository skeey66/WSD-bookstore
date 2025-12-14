CREATE TABLE IF NOT EXISTS wishlist (
                                        user_id BIGINT NOT NULL,
                                        book_id BIGINT NOT NULL,
                                        created_at DATETIME NOT NULL,
                                        PRIMARY KEY (user_id, book_id),
    INDEX idx_wishlist_user_id (user_id),
    INDEX idx_wishlist_book_id (book_id),
    CONSTRAINT fk_wishlist_user_id FOREIGN KEY (user_id) REFERENCES `user`(user_id),
    CONSTRAINT fk_wishlist_book_id FOREIGN KEY (book_id) REFERENCES book(book_id)
    );
