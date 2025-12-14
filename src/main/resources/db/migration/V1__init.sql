CREATE TABLE IF NOT EXISTS `user` (
    `user_id` BIGINT NOT NULL AUTO_INCREMENT,
    `password_hash` VARCHAR(255) NOT NULL,
    `name` VARCHAR(100) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `gender` VARCHAR(20) NOT NULL,
    `birth_date` DATE NOT NULL,
    `address` VARCHAR(255) NOT NULL,
    `phone_number` VARCHAR(30) NOT NULL,
    `created_at` DATETIME NULL,
    `updated_at` DATETIME NULL,
    `deleted_at` DATETIME NULL,
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `uk_user_email` (`email`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
