CREATE TABLE IF NOT EXISTS carts (
                                     cart_id BIGINT NOT NULL AUTO_INCREMENT,
                                     user_id BIGINT NOT NULL,
                                     created_at DATETIME NOT NULL,
                                     updated_at DATETIME NOT NULL,
                                     deleted_at DATETIME NULL,
                                     PRIMARY KEY (cart_id),
    UNIQUE KEY uq_carts_user_id (user_id),
    INDEX idx_carts_user_id (user_id),
    CONSTRAINT fk_carts_user_id FOREIGN KEY (user_id) REFERENCES `user`(user_id)
    );

CREATE TABLE IF NOT EXISTS cart_items (
                                          cartitem_id BIGINT NOT NULL AUTO_INCREMENT,
                                          cart_id BIGINT NOT NULL,
                                          book_id BIGINT NOT NULL,
                                          unit_price DECIMAL(10,2) NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY (cartitem_id),
    UNIQUE KEY uq_cart_items_cart_book (cart_id, book_id),
    INDEX idx_cart_items_cart_id (cart_id),
    INDEX idx_cart_items_book_id (book_id),
    CONSTRAINT fk_cart_items_cart_id FOREIGN KEY (cart_id) REFERENCES carts(cart_id),
    CONSTRAINT fk_cart_items_book_id FOREIGN KEY (book_id) REFERENCES book(book_id)
    );
