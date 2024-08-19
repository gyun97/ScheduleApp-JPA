# ScheduleApp-JPA

<br>
<br>

# API 명세서

<br>

## 1. 일정 API
| 기능    | Method | URL             | request       | response | 상태 코드       |
|-------|--------|-----------------|---------------|----------|-------------|
| 일정 등록 | POST   | /schedules      | request body  | 등록 정보    | 200 : 정상 등록 |
| 일정 조회 | GET    | /schedules/{id} | request param | 단건 응답 정보 | 200 : 정상 조회 |
| 일정 수정 | PUT    | /schedules/{id} | request body  | 수정 정보    | 200 : 정상 수정 |

<br>

## 2. 댓글 API


| 기능       | Method | URL            | request       | response | 상태 코드       |
|----------|--------|----------------|---------------|----------|-------------|
| 댓글 등록    | POST   | /comments      | request body  | 등록 정보    | 200 : 정상 등록 |
| 댓글 조회    | GET    | /comments/{id} | request param | 단건 응답 정보 | 200 : 정상 조회  |
| 댓글 목록 조회 | GET    | /comments      | request body  | 다건 응답 정보 | 200 : 정상 조회  |
| 댓글 수정    | PUT    | /comments/{id} | request body  | 수정 정보    | 200 : 정상 수정 |
| 댓글 삭제    | DELETE | /comments/{id} | request param | 삭제 정     | 200 : 정상 삭제 |

<br>


## 3. 유저 API


| 기능       | Method | URL            | request       | response | 상태 코드       |
|----------|--------|----------------|---------------|----------|-------------|
| 유저 등록    | POST   | /users         | request body  | 등록 정보    | 200 : 정상 등록 |
| 유저 조회    | GET    | /users/{id} | request param | 단건 응답 정보 | 200 : 정상 조회  |
| 유저 목록 조회 | GET    | /users      | request body  | 다건 응답 정보 | 200 : 정상 조회  |
| 유저 수정    | PUT    | /users/{id} | request body  | 수정 정보    | 200 : 정상 수정 |
| 유저 삭제    | DELETE | /users/{id} | request param | 삭제 정보    | 200 : 정상 삭제 |


<br>
<br>


# ERD

![image](https://github.com/user-attachments/assets/66d7d8b2-dea7-476b-a7c8-07ce9401184b)



<br>
<br>

# 테이블 생성 SQL

```sql
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
```
