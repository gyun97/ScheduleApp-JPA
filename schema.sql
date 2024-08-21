CREATE TABLE `schedule` (
                            `schedule_id` BIGINT NOT NULL AUTO_INCREMENT,
                            `user_name` VARCHAR(20) NULL,
                            `title` VARCHAR(50) NOT NULL,
                            `content` VARCHAR(200) NOT NULL,
                            `created_at` DATETIME NULL,
                            `updated_at` DATETIME NULL,
                            PRIMARY KEY (`schedule_id`)
);

CREATE TABLE `user` (
                        `id` BIGINT NOT NULL AUTO_INCREMENT,
                        `name` VARCHAR(20) NOT NULL,
                        `email` VARCHAR(20) NOT NULL,
                        `created_at` DATETIME NOT NULL,
                        `updated_at` DATETIME NULL,
                        PRIMARY KEY (`id`)
);

CREATE TABLE `comment` (
                           `id` BIGINT NOT NULL AUTO_INCREMENT,
                           `content` VARCHAR(200) NOT NULL,
                           `user_name` VARCHAR(20) NULL,
                           `created_at` DATETIME NULL,
                           `updated_at` DATETIME NULL,
                           `schedule_id` BIGINT NOT NULL,
                           PRIMARY KEY (`id`),
                           FOREIGN KEY (`schedule_id`) REFERENCES `schedule` (`id`)
);

CREATE TABLE `schedule_user_map` (
                                     `id` BIGINT NOT NULL AUTO_INCREMENT,
                                     `schedule_id` BIGINT NOT NULL,
                                     `user_id` BIGINT NOT NULL,
                                     PRIMARY KEY (`id`),
                                     FOREIGN KEY (`schedule_id`) REFERENCES `schedule` (`id`),
                                     FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);
