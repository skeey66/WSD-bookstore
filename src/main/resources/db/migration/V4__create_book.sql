CREATE TABLE `book` (
                        `book_id` BIGINT NOT NULL AUTO_INCREMENT,
                        `isbn` VARCHAR(50) NOT NULL,
                        `publisher` VARCHAR(255) NOT NULL,
                        `title` VARCHAR(255) NOT NULL,
                        `summary` TEXT NOT NULL,
                        `price` DECIMAL(15,2) NOT NULL,
                        `publication_year` INT NOT NULL,
                        `created_at` DATETIME NULL,
                        `updated_at` DATETIME NULL,
                        PRIMARY KEY (`book_id`),
                        UNIQUE KEY `uk_book_isbn` (`isbn`)
);
