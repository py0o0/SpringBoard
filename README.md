# 목차

1. [프로젝트 목적](#프로젝트-목적)
2. [프로젝트 소개](#프로젝트-소개)
3. [사용 기술](#사용-기술)
4. [주요 기능](#주요-기능)
5. [API 명세](#api-명세)
6. [SnapShot](#snapshot)

# 프로젝트 목적

MCV 패턴, MyBatis, SpringSecurity 학습

# 프로젝트 소개

스프링을 배우고 스프링으로 혼자 간단히 만들 수 있는 게시판을 직접 만들어보고 배우기 위해 제작하였습니다.

 ## ERD
 <img src= "https://github.com/user-attachments/assets/dfe5eb02-32ff-4d54-9a16-46ba41f6222f" alt="DB" width="100%" height="auto"> 

# 사용 기술

## 3-1 백엔드

### 주요 프레임워크 / 라이브러리
- **Java**
- **SpringBoot**
- **Mybatis**
- **Thymeleaf**
- **SpringSecurity**
  
### Build Tool
- **Gradle**
  
### DataBase
- **MySQL**

## 3-2 프론트엔드
- **HTML**
- **JavaScript**
- **css**

# 주요 기능

---

## 1. 접근 권한 관리
- **익명 사용자 (Anonymous)**  
  - 로그인 및 회원가입, 홈 화면 접근 가능.  
- **관리자 (ADMIN)**  
  - 사용자 관리 기능 제공 (유저 추방 및 게시글 삭제 가능).  

---

## 2. 회원가입 및 로그인
### 회원가입
- 클라이언트에서 ID와 비밀번호 입력 후 회원가입 요청.  
- 입력된 ID에 대한 중복 여부를 확인하고, 중복되지 않을 경우 회원가입 성공.
- 관리자 계정은 관리자만이 생성가능.  

### 로그인
- 클라이언트에서 입력한 ID와 비밀번호를 데이터베이스에서 조회.  
- 인증 성공 시, Spring Security 세션에 사용자 정보를 저장.  

---

## 3. 게시글 관리
### 게시글 작성 및 수정
- 로그인 상태에서만 게시글 작성 가능.  
- 작성자는 자신의 게시글에 대해 수정 및 삭제 가능.  

### 게시글 기능
- **조회수 증가**: 게시글을 열람할 때 조회수가 증가.  
- **댓글 작성**: 게시글에 댓글 추가 가능.  
- **검색**: 제목 및 내용 기반으로 게시글 검색 가능.  
- **페이징**: `Pageable` 객체를 사용하여 페이지네이션 구현.  

---

## 4. 좋아요 기능
### 좋아요 추가 및 제한
- 로그인 상태에서 게시글에 좋아요 가능.  
- 본인이 작성한 게시글이나 이미 좋아요를 누른 게시글에는 좋아요 불가.  

---

## 5. 마이페이지
### 개인화된 정보 제공
- 로그인 사용자만 접근 가능.  
- 자신이 작성한 글, 좋아요한 글, 댓글을 작성한 글을 한눈에 확인 가능.  

---

## 6. 보안 및 사용자 경험 강화
- Spring Security를 활용한 세션 관리로 안전한 인증 및 접근 권한 관리.  

---

#  API 명세

## Member API

| HTTP 메서드 | URL               | 설명                          | 요청 파라미터                                               | 응답                                          |
|-------------|-------------------|-------------------------------|-----------------------------------------------------------|-----------------------------------------------|
| **GET**     | `/login`           | 로그인 페이지 요청            | 없음                                                      | HTML 페이지                                   |
| **POST**    | `/loginProc`       | 로그인 처리                    | 없음                                                      | Redirect("/")                                 |
| **GET**     | `/join`            | 회원가입 페이지 요청          | 없음                                                      | HTML 페이지                                   |
| **POST**    | `/joinProc`        | 회원가입 처리                  | Body: { "userId": "string", "userPass": "string" }         | 성공: `Redirect("/join?success=true")`<br>실패: `Redirect("/join?error=true")` |
| **GET**     | `/logout`          | 로그아웃 처리                  | 없음                                                      | 없음                                          |
| **GET**     | `/myPage`          | 마이페이지 요청                | 없음                                                      | HTML 페이지                                   |
| **GET**     | `/admin`           | 관리자 페이지 요청             | 없음                                                      | HTML 페이지                                   |
| **GET**     | `/admin/userManage`| 유저 관리 페이지 요청          | 없음                                                      | HTML 페이지 (유저 목록, 페이징 처리)         |
| **GET**     | `/admin/deleteUser/{userId}` | 특정 사용자 삭제 요청  | Path: `userId` (삭제할 사용자 ID)                        | Redirect(`/admin/userManage`)                 |
| **GET**     | `/admin/join`      | 관리자 회원가입 페이지 요청    | 없음                                                      | HTML 페이지                                   |
| **POST**    | `/admin/joinProc`  | 관리자 회원가입 처리            | Body: { "userId": "string", "userPass": "string" }         | 성공: `Redirect("/admin/join?success=true")`<br>실패: `Redirect("/admin/join?error=true")` |


## Board API

| HTTP 메서드 | URL                           | 설명                                 | 요청 파라미터                                                                                       | 응답                                |
|-------------|-------------------------------|--------------------------------------|----------------------------------------------------------------------------------------------------|-------------------------------------|
| **GET**     | `/`                           | 메인 페이지 요청                     | Query: `page` (페이지 번호), `size` (페이지 크기, 기본값 5)                                         | HTML 페이지                         |
| **GET**     | `/writePage`                  | 글 작성 페이지 요청                  | 없음                                                                                               | HTML 페이지                         |
| **POST**    | `/write`                      | 글 작성 요청                         | Body: { "boardTitle": "string", "boardContents": "string" }                                         | Redirect("/")                       |
| **GET**     | `/board/{boardId}`            | 게시글 상세보기 요청                 | Path: `boardId` (게시글 ID)                                                                        | HTML 페이지                         |
| **GET**     | `/like`                       | 게시글 좋아요 요청                   | Query: `boardId` (게시글 ID), `userId` (사용자 ID)                                                 | JSON: { "success": true/false }     |
| **GET**     | `/commentWrite/{boardId}`     | 댓글 작성 페이지 요청                | Path: `boardId` (게시글 ID)                                                                        | HTML 페이지                         |
| **POST**    | `/commentWrite/{boardId}`     | 댓글 작성 요청                       | Path: `boardId` (게시글 ID), Body: { "commentContents": "string" }                                 | Redirect(`/board/{boardId}`)        |
| **GET**     | `/board/modify/{boardId}`     | 게시글 수정 페이지 요청              | Path: `boardId` (게시글 ID)                                                                        | HTML 페이지                         |
| **POST**    | `/modify/{boardId}`           | 게시글 수정 요청                     | Path: `boardId` (게시글 ID), Body: { "boardTitle": "string", "boardContents": "string" }           | Redirect(`/board/{boardId}`)        |
| **GET**     | `/board/remove/{boardId}`     | 게시글 삭제 요청                     | Path: `boardId` (게시글 ID)                                                                        | Redirect("/")                       |
| **GET**     | `/my/like`                    | 내가 좋아한 게시글 목록 요청         | Query: `page` (페이지 번호), `size` (페이지 크기, 기본값 5)                                         | HTML 페이지                         |
| **GET**     | `/my/comment`                 | 내가 댓글 작성한 게시글 목록 요청    | Query: `page` (페이지 번호), `size` (페이지 크기, 기본값 5)                                         | HTML 페이지                         |
| **GET**     | `/my/board`                   | 내가 작성한 게시글 목록 요청         | Query: `page` (페이지 번호), `size` (페이지 크기, 기본값 5)                                         | HTML 페이지                         |
| **POST**    | `/search`                     | 게시글 검색 요청                     | Body: { "input": "string" }                                                                        | HTML 페이지                         |
| **GET**     | `/userBoard/{userId}`         | 특정 사용자가 작성한 게시글 목록 요청 | Path: `userId` (사용자 ID), Query: `page` (페이지 번호), `size` (페이지 크기, 기본값 5)             | HTML 페이지                         |


# SnapShop

## 시스템 개요
아래는 프로젝트의 주요 화면 스냅샷입니다. 기능별로 이미지를 분류하고 간단한 설명을 추가하였습니다.

---

### 유저 권한에 따른 홈 화면
<div style="border: 1px solid #ddd; padding: 10px; margin-bottom: 20px;">
    <img src="https://github.com/user-attachments/assets/ab7b8609-67cc-49b5-8264-96365f53b26e" alt="메인 페이지-익명" width="100%" height="auto" style="border: 1px solid #ccc; padding: 5px; margin-bottom: 10px;">
    <p><strong>메인 페이지-익명</strong>: 비회원 사용자를 위한 기본 화면으로 인기 게시글과 최신 정보를 제공합니다.</p>
</div>
<div style="border: 1px solid #ddd; padding: 10px; margin-bottom: 20px;">
    <img src="https://github.com/user-attachments/assets/29efc7fe-3742-46c2-b222-6a05e4eefa3a" alt="메인 페이지-유저" width="100%" height="auto" style="border: 1px solid #ccc; padding: 5px; margin-bottom: 10px;">
    <p><strong>메인 페이지-유저</strong>: 로그인한 사용자에게 맞춤 정보를 제공합니다.</p>
</div>
<div style="border: 1px solid #ddd; padding: 10px; margin-bottom: 20px;">
    <img src="https://github.com/user-attachments/assets/6c073e8f-1806-4f90-9443-fd82b1372cc1" alt="메인 페이지-어드민" width="100%" height="auto" style="border: 1px solid #ccc; padding: 5px; margin-bottom: 10px;">
    <p><strong>메인 페이지-어드민</strong>: 관리자 전용 기능이 추가된 화면입니다.</p>
</div>

---

### 마이페이지 및 내가 좋아한 글/작성한 글 확인
<div style="border: 1px solid #ddd; padding: 10px; margin-bottom: 20px;">
    <img src="https://github.com/user-attachments/assets/25e1ae8d-e352-40b5-8691-c83ec6e9c262" alt="마이페이지" width="100%" height="auto" style="border: 1px solid #ccc; padding: 5px; margin-bottom: 10px;">
    <p><strong>마이페이지</strong>: 사용자 정보를 확인하고 설정할 수 있는 화면입니다.</p>
</div>

---

### 게시글 및 댓글 관련 화면
<div style="border: 1px solid #ddd; padding: 10px; margin-bottom: 20px;">
    <img src="https://github.com/user-attachments/assets/9a4e29e5-36a3-4ca8-86a3-b77fd0352662" alt="게시글" width="100%" height="auto" style="border: 1px solid #ccc; padding: 5px; margin-bottom: 10px;">
    <p><strong>게시글</strong>: 게시글 목록을 확인할 수 있는 화면입니다.</p>
</div>

---

### 관리자 페이지 및 사용자 관리
<div style="border: 1px solid #ddd; padding: 10px; margin-bottom: 20px;">
    <img src="https://github.com/user-attachments/assets/bcbc8412-06f4-4b37-b8c6-9962bdeca1e4" alt="관리자 페이지" width="100%" height="auto" style="border: 1px solid #ccc; padding: 5px; margin-bottom: 10px;">
    <p><strong>관리자 페이지</strong>: 시스템 관리 및 설정을 위한 관리자 전용 화면입니다.</p>
</div>
<div style="border: 1px solid #ddd; padding: 10px; margin-bottom: 20px;">
    <img src="https://github.com/user-attachments/assets/cc7ab553-cb78-4c97-97a5-3677ee43706c" alt="유저 관리 페이지" width="100%" height="auto" style="border: 1px solid #ccc; padding: 5px; margin-bottom: 10px;">
    <p><strong>유저 관리 페이지</strong>: 관리자가 사용자 정보를 조회하고 관리할 수 있는 화면입니다.</p>
</div>
