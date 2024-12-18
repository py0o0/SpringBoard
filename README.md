# 목차

1. [프로젝트 목적](#프로젝트-목적)
1. [프로젝트 소개](#프로젝트-소개)
2. [사용 기술](#사용-기술)
3. [주요 기능](#주요-기능)
4. [API 명세](#api-명세)
<br></br>

# 프로젝트 목적

MCV 패턴, MyBatis, SpringSecurity 학습
<hr/>

# 프로젝트 소개

스프링을 배우고 스프링으로 혼자 간단히 만들 수 있는 게시판을 직접 만들어보고 배우기 위해 제작하였습니다.


 ## 테이블 스키마
 <img src= "https://github.com/user-attachments/assets/f41ca2b9-8290-4518-9750-77b15b1c1596" alt="DB" width="100%" height="auto"> 
<hr/>

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
<hr/>

# 주요 기능

- **접근 권한**:
  - Anonymous 사용자:로그인, 회원가입, 홈 접근 가능

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

- **좋아요**: 
  - 로그인 상태일 시 게시글에 좋아요 기능 가능
  - 자신의 작성한 게시물과 한번 좋아요한 게시글에는 좋아요 불가능
 
- **마이페이지**: 
  - 로그인 상태일 시 자신이 작성한 글, 좋아요한 글, 댓글을 남긴 글 조회 가능

<hr/>

#  API 명세

## Member API


| HTTP 메서드 | URL           | 설명               | 요청 파라미터                                               | 응답                        |
|-------------|---------------|--------------------|-----------------------------------------------------------|-----------------------------|
| GET         | /login        | 로그인 페이지 요청 | 없음                                                      | HTML 페이지                 |
| POST        | /loginProc    | 로그인 요청        | 없음                                                      | Redirect("/")               |
| GET         | /join         | 회원가입 페이지 요청| 없음                                                      | HTML 페이지                 |
| POST        | /joinProc     | 회원가입 요청      | Body: { "userId": "string", "userPass": "string" } | 성공: Redirect("/join?success=true") 실패: Redirect("/join?error=true") |
| GET         | /logout       | 로그아웃 요청      | 없음                                                      | 없음                        |
| GET         | /myPage       | 마이페이지 요청    | 없음                                                      | HTML 페이지                 |                                                                                                                                   | Redirect (`/`)               | 로그아웃 후 메인 페이지로 리다이렉션.                                    |

<hr/>

## Board API

| HTTP 메서드 | URL                         | 설명                         | 요청 파라미터                                                                          | 응답                       |
|-------------|-----------------------------|------------------------------|---------------------------------------------------------------------------------------|----------------------------|
| GET         | /                           | 홈 페이지 요청                | Model: 사용자 정보(id, principal), 게시글 리스트(boardList)                           | HTML 페이지 ("home")       |
| GET         | /writePage                  | 게시글 작성 페이지 요청        | 없음                                                                                  | HTML 페이지 ("writePage")  |
| POST        | /write                      | 게시글 작성 요청              | Body: { "title": "string", "content": "string" }                                      | Redirect("/")              |
| GET         | /board/{boardId}            | 게시글 상세 조회              | PathVariable: boardId<br>Model: 게시글 정보(board), 댓글 리스트(commentList), 사용자 ID | HTML 페이지 ("board")      |
| GET         | /like                       | 게시글 좋아요 요청            | Query: boardId, userId                                                                | JSON (Boolean 성공 여부)   |
| GET         | /commentWrite/{boardId}     | 댓글 작성 페이지 요청          | PathVariable: boardId                                                                 | HTML 페이지 ("commentWrite") |
| POST        | /commentWrite/{boardId}     | 댓글 작성 요청                | PathVariable: boardId<br>Body: { "content": "string" }                                | Redirect("/board/{boardId}") |
| GET         | /board/modify/{boardId}     | 게시글 수정 페이지 요청        | PathVariable: boardId                                                                 | HTML 페이지 ("boardModify") |
| POST        | /modify/{boardId}           | 게시글 수정 요청              | PathVariable: boardId<br>Body: { "title": "string", "content": "string" }             | Redirect("/board/{boardId}") |
| GET         | /board/remove/{boardId}     | 게시글 삭제 요청              | PathVariable: boardId                                                                 | Redirect("/")              |
| GET         | /my/like                    | 내가 좋아요 누른 글 조회       | Model: 좋아요한 게시글 리스트(boardList)                                              | HTML 페이지 ("myBoard")    |
| GET         | /my/comment                 | 내가 댓글을 단 글 조회         | Model: 댓글을 단 게시글 리스트(boardList)                                             | HTML 페이지 ("myBoard")    |
| GET         | /my/board                   | 내가 작성한 글 조회            | Model: 작성한 게시글 리스트(boardList)                                                | HTML 페이지 ("myBoard")    |
| POST        | /search                     | 게시글 검색 요청              | Body: { "input": "string" }                                                           | HTML 페이지 ("myBoard")    |
