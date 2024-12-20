# 목차

1. [프로젝트 목적](#프로젝트-목적)
1. [프로젝트 소개](#프로젝트-소개)
2. [사용 기술](#사용-기술)
3. [주요 기능](#주요-기능)
4. [API 명세](#api-명세)

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

- **접근 권한**:
  - Anonymous 사용자:로그인, 회원가입, 홈 접근 가능
  - ADMIN: 유저 추방, 게시글 삭제 가능

- **회원 가입/로그인**: 
  - client에서 id, password 입력
 
  - **회원가입**
    - id 중목 검사
 
  - **로그인**
    - id, password DB 조회
    - 로그인 성공 시 Security세션에 저장

- **게시글**: 
  - 로그인 상태일 시 게시글 작성 가능
  - 작성자의 경우 게시글 수정 및 삭제 가능
  - 조회수 기능 구현
  - 댓글 기능 구현
  - 제목, 내용으로 게시글 검색 기능 구현
  - Pageable 객체 사용하여 페이징 구현

- **좋아요**: 
  - 로그인 상태일 시 게시글에 좋아요 기능 가능
  - 자신의 작성한 게시물과 한번 좋아요한 게시글에는 좋아요 불가능
 
- **마이페이지**: 
  - 로그인 상태일 시 자신이 작성한 글, 좋아요한 글, 댓글을 남긴 글 조회 가능

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

