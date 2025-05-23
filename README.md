# 🗕 일정 관리 API 명세서

## 기본 정보

* **기능**: 일정 작성, 조회, 수정, 삭제
* **데이터 형식**: JSON
* **Base URL**: `/schedules`

---

## 📌 일정 생성

* **URL**: `/schedules`
* **Method**: `POST`
* **Request Body**:

```json
{
  "title": "스터디 준비하기",
  "username": "홍길동",
  "password": "1234",
  "date": "2025-05-20T15:00:00"
}
```

* **Response**:

```json
{
  "id": 1,
  "title": "스터디 준비하기",
  "username": "홍길동",
  "dateCreated": "2025-05-20T15:00:00",
  "dateModified": "2025-05-20T15:00:00"
}
```

---

## 📋 전체 일정 조회 (조건부 필터링)

* **URL**: `/schedules`
* **Method**: `GET`
* **Query Params (선택사항)**:

    * `modifiedDate=2025-05-20`
    * `username=홍길동`
* **Response**:

```json
[
  {
    "id": 1,
    "title": "스터디 준비하기",
    "username": "홍길동",
    "dateCreated": "2025-05-20T15:00:00",
    "dateModified": "2025-05-20T15:00:00"
  }
]
```

---

## 🔍 선택 일정 조회

* **URL**: `/schedules/{id}`
* **Method**: `GET`
* **Response**:

```json
{
  "id": 1,
  "title": "스터디 준비하기",
  "username": "홍길동",
  "dateCreated": "2025-05-20T15:00:00",
  "dateModified": "2025-05-20T15:00:00"
}
```

---

## ✏️ 일정 수정

* **URL**: `/schedules/{id}`
* **Method**: `PUT`
* **Request Body**:

```json
{
  "title": "수정된 할 일",
  "password": "1234"
}
```

* **Response**:

```json
{
  "id": 1,
  "title": "수정된 할 일",
  "username": "홍길동",
  "dateCreated": "2025-05-20T15:00:00",
  "dateModified": "2025-05-20T16:00:00"
}
```

---

## 🗑 일정 삭제

* **URL**: `/schedules/{id}`
* **Method**: `DELETE`
* **Request Body**:

```json
{
  "password": "1234"
}
```

* **Response**: HTTP 204 No Content (body 없음)

---

## 🖾 ERD 설계

### ✅ Schedule Table

| 필드명          | 타입            | 설명       |
| ------------ | ------------- | -------- |
| id           | Long          | 일정 고유 ID |
| title        | String        | 할 일 제목   |
| username     | String        | 작성자명     |
| password     | String        | 비밀번호     |
| dateCreated  | LocalDateTime | 작성일      |
| dateModified | LocalDateTime | 수정일      |

* `id`는 자동 증가 (Auto Increment 또는 UUID)
* `dateModified`는 수정될 때만바 갱신됨
* 초기 생성 시 `dateCreated`와 `dateModified`는 동일
