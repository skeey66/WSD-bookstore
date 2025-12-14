CREATE TABLE orders (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        user_id BIGINT NOT NULL,
                        status VARCHAR(30) NOT NULL,
                        total_amount DECIMAL(15,2) NOT NULL,
                        created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        deleted_at DATETIME NULL
);

CREATE INDEX idx_orders_user_id_created_at ON orders(user_id, created_at);
CREATE INDEX idx_orders_status_created_at ON orders(status, created_at);

CREATE TABLE order_items (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             order_id BIGINT NOT NULL,
                             book_id BIGINT NOT NULL,
                             unit_price DECIMAL(15,2) NOT NULL,
                             quantity INT NOT NULL,
                             CONSTRAINT fk_order_items_order_id FOREIGN KEY (order_id) REFERENCES orders(id)

);

CREATE INDEX idx_order_items_order_id ON order_items(order_id);
CREATE INDEX idx_order_items_book_id ON order_items(book_id);
