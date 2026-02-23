# Swagger MCP Server 
> Spring Boot API를 Swagger 기반으로 Claude와 연결하기 위한 MCP 서버 프로젝트

---

## 📌 프로젝트 개요

이 프로젝트는 **Spring Boot로 개발한 REST API**를 **Swagger(OpenAPI)** 문서를 통해 자동으로 분석하고,  
이를 **Claude Desktop과 연결(MCP 서버)** 한다.

- API 목록 파악
- API 동작 설명
- 실제 API 호출 테스트
- 개발 중 API 검증 및 분석

을 **자연어 기반으로 수행**할 수 있도록 만든 프로젝트입니다.

---

## 🎯 프로젝트 목표

- Spring Boot API 개발 시
  - Swagger 문서를 직접 보지 않아도
  - Claude에게 질문만으로 API 구조 이해
- API 테스트를 curl / Postman 없이 Claude로 수행
- 개발 생산성 향상 (AI 기반 API Assistant)


---
## 🧱 ERD
<img width=100% height="332" alt="MiniShoppingMall" src="https://github.com/user-attachments/assets/0cc1bcf0-d983-4a0b-8231-5cb4c9242047" />

## 🎯 프로젝트 - [미니 쇼핑몰]
[사용자]
- 회원 조회
- 회원 생성

[상품]
- 상품 목록 조회
- 상품 상세 조회
- 상품 등록

[주문]
- 주문생성
- 사용자별 주문 조회

## 🧱 전체 아키텍처

---

## ⚙️ 기술 스택

### Backend (API Server)
- Java 17
- Spring Boot
- Spring Web MVC
- **springdoc-openapi** (Swagger 자동 문서화)

### MCP Server
- Node.js
- @modelcontextprotocol/sdk
- Zod (Schema validation)
- STDIO transport

### AI Client
- Claude Desktop
- MCP (Model Context Protocol)

---

## 🧩 Spring Boot 설정

### 1️⃣ Swagger(OpenAPI) 의존성

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.3.0</version>
</dependency> ```
