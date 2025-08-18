# 🌐 Cloud Rest - 취업 지원 관리 플랫폼

## 📌 프로젝트 소개
**Cloud Rest**는 구직자와 기업 간 원활한 채용 프로세스를 지원하는 **취업 지원 관리 플랫폼**입니다.  
사용자는 이력서를 등록하고 공고에 지원할 수 있으며, 기업은 공고를 등록하고 지원자를 관리할 수 있습니다.  

---

## 🛠 기술 스택
| 구분 | 기술 |
|------|------|
| Language | Java 17, SQL |
| Framework | Spring Boot, Spring Data JPA |
| Database | MySQL |
| ORM | Hibernate |
| Build Tool | Gradle |
| 기타 | Lombok, JWT, JPA Auditing |

---

## 🚀 주요 기능
- **회원 관리**
  - 일반 사용자 및 기업 회원가입 / 로그인 (JWT 인증)
- **이력서 관리**
  - 이력서 CRUD
  - 스킬 태그 연동
- **채용 공고 관리**
  - 기업 회원의 공고 등록 / 수정 / 삭제
- **지원 관리**
  - 사용자의 공고 지원
  - 지원 현황 조회
- **알림 관리**
  - 공고 등록 시 관심 유저에게 알림 발송
- **게시판**
  - Q&A 게시판 (문의 및 답변 등록)

---

## 🗂 ERD & 테이블 구조
- 주요 테이블:  
  `user`, `corp`, `resume`, `skill`, `resume_skill`,  
  `recruit`, `apply`, `notification`, `board`, `qna_board`, `qna_answer`

> <img width="2593" height="3524" alt="2조 ERD 다이어그램" src="https://github.com/user-attachments/assets/7d7e0825-bd55-486e-b99d-659d1baea4af" />


---

## 📑 API 명세
- 회원가입 / 로그인  
- 이력서 CRUD  
- 공고 CRUD  
- 지원하기  
- 알림 관리  
- 게시판(Q&A)  


## 🏗 프로젝트 아키텍처(3계층 구조)

- **Controller Layer**  
  - REST API 요청/응답 처리  

- **Service Layer**  
  - 비즈니스 로직 담당  

- **Repository Layer**  
  - DB 접근 (JPA)  

---

## ⚡ 주요 기술 스택

- **Spring Web, Spring Security**  
- **JPA (Hibernate) or MyBatis**  
- **JWT Token 기반 인증/인가**  
- **Validation (javax.validation)**  
- **Swagger 문서 자동화**

