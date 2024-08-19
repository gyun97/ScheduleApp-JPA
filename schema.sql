CREATE TABLE `schedule` (
                            `id`	BIGINT	NOT NULL,
                            `user_name`	VARCHAR(20)	NOT NULL,
                            `title`	VARCHAR(50)	NOT NULL,
                            `content`	VARCHAR(200)	NOT NULL,
                            `created_at`	DATETIME	NULL,
                            `updated_at`	DATETIME	NULL
);

CREATE TABLE `user` (
                        `id`	BIGINT	NOT NULL,
                        `name`	VARCHAR(20)	NOT NULL,
                        `email`	VARCHAR(20)	NOT NULL,
                        `created_at`	DATETIME	NOT NULL,
                        `updated_at`	DATETIME	NULL
);

CREATE TABLE `comment` (
                           `id`	BIGINT	NOT NULL,
                           `content`	VARCHAR(200)	NOT NULL,
                           `user_name`	VARCHAR(20)	NULL,
                           `created_at`	DATETIME	NULL,
                           `updated_at`	DATETIME	NULL,
                           `schedule_id`	BIGINT	NOT NULL
);

CREATE TABLE `schedule_user_map` (
                                     `schedule_id`	BIGINT	NOT NULL,
                                     `user_id`	BIGINT	NOT NULL
);

ALTER TABLE `schedule` ADD CONSTRAINT `PK_SCHEDULE` PRIMARY KEY (
                                                                 `id`
    );

ALTER TABLE `user` ADD CONSTRAINT `PK_USER` PRIMARY KEY (
                                                         `id`
    );

ALTER TABLE `comment` ADD CONSTRAINT `PK_COMMENT` PRIMARY KEY (
                                                               `id`
    );

ALTER TABLE `schedule_user_map` ADD CONSTRAINT `PK_SCHEDULE_USER_MAP` PRIMARY KEY (
                                                                                   `schedule_id`,
                                                                                   `user_id`
    );

ALTER TABLE `schedule_user_map` ADD CONSTRAINT `FK_schedule_TO_schedule_user_map_1` FOREIGN KEY (
                                                                                                 `schedule_id`
    )
    REFERENCES `schedule` (
                           `id`
        );

ALTER TABLE `schedule_user_map` ADD CONSTRAINT `FK_user_TO_schedule_user_map_1` FOREIGN KEY (
                                                                                             `user_id`
    )
    REFERENCES `user` (
                       `id`
        );

