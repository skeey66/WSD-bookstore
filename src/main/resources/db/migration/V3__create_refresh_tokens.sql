CREATE TABLE IF NOT EXISTS `refresh_tokens` (
    `refresh_token_id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    `token` VARCHAR(500) NOT NULL,
    `expires_at` DATETIME(6) NOT NULL,
    `revoked_at` DATETIME(6) NULL,
    `created_at` DATETIME(6) NOT NULL,
    PRIMARY KEY (`refresh_token_id`),
    UNIQUE KEY `UK_refresh_token_token` (`token`),
    KEY `IDX_refresh_token_user_id` (`user_id`),
    CONSTRAINT `FK_refresh_tokens_user`
    FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
