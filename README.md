# ScheduleApp-JPA

<br>

# API 명세서

<br>

## 1. 일정 API
| 기능    | Method | URL             | request       | reponse  | 상태 코드       |
|-------|--------|-----------------|---------------|----------|-------------|
| 일정 등록 | POST   | /schedules      | request body  | 등록 정보    | 200 : 정상 등록 |
| 일정 조회 | GET    | /schedules/{id} | request param | 단건 응답 정보 | 200 : 정상 조  |
| 일정 수정 | PUT    | /schedules/{id} | request body  | 수정 정보    | 200 : 정상 수정 |

<br>

## 2. 댓글 API


| 기능       | Method | URL            | request       | reponse  | 상태 코드       |
|----------|--------|----------------|---------------|----------|-------------|
| 댓글 등록    | POST   | /comments      | request body  | 등록 정보    | 200 : 정상 등록 |
| 댓글 조회    | GET    | /comments/{id} | request param | 단건 응답 정보 | 200 : 정상 조  |
| 댓글 목록 조회 | GET    | /comments      | request body  | 다건 응답 정보 | 200 : 정상 조  |
| 댓글 수정    | PUT    | /comments/{id} | request body  | 수정 정보    | 200 : 정상 수정 |
| 댓글 삭제    | DELETE | /comments/{id} | request param | 삭제 정     | 200 : 정상 삭제 |

<br>


## 3. 유저 API


| 기능       | Method | URL            | request       | reponse | 상태 코드       |
|----------|--------|----------------|---------------|--------|-------------|
| 유저 등록    | POST   | /users         | request body  | 등록 정보  | 200 : 정상 등록 |
| 유저 조회    | GET    | /users/{id} | request param | 단건 응답 정보 | 200 : 정상 조  |
| 유저 목록 조회 | GET    | /users      | request body  | 다건 응답 정보 | 200 : 정상 조  |
| 유저 수정    | PUT    | /users/{id} | request body  | 수정 정보  | 200 : 정상 수정 |
| 유저 삭제    | DELETE | /users/{id} | request param | 삭제 정보   | 200 : 정상 삭제 |
