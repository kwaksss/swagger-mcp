# Swagger MCP Server 🚀  
> Spring Boot API를 Swagger(OpenAPI) 기반으로 Claude와 연결하기 위한 MCP 서버 프로젝트

---

## 📌 프로젝트 개요

이 프로젝트는 **Spring Boot로 개발한 REST API**를  
**Swagger(OpenAPI)** 문서를 통해 자동으로 분석하고,  
이를 **Claude Desktop과 연결(MCP 서버)** 하여

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
</dependency>



# 🛠 Troubleshooting

Swagger MCP Server 프로젝트를 진행하며 발생한 주요 문제와  
각 문제에 대한 **문제 / 문제 해결 과정 / 결과**를 정리했습니다.

---

## 1️⃣ Maven 명령어 실행 불가

### ❗ 문제
```bash
mvn clean package
mvn 명령어를 인식하지 못하는 오류 발생

🔍 문제 해결 과정
Maven이 전역 PATH에 등록되어 있지 않음

IntelliJ는 내부적으로 Maven Wrapper(mvnw)를 사용 중

CLI에서도 동일한 환경을 사용하도록 Maven Wrapper 사용

bash
코드 복사
mvnw.cmd clean package
✅ 결과
별도 Maven 설치 없이 빌드 가능

환경 의존성 제거 및 CI 환경 호환성 확보

2️⃣ Node.js MCP 서버 ES Module 오류
❗ 문제
text
코드 복사
SyntaxError: Cannot use import statement outside a module
🔍 문제 해결 과정
MCP SDK는 ES Module 기반

package.json이 commonjs로 설정되어 있었음

"type": "module"로 변경

json
코드 복사
{
  "type": "module"
}
✅ 결과
ES Module 문법 정상 인식

MCP SDK 정상 동작

3️⃣ Claude MCP 설정 JSON 파싱 오류
❗ 문제
text
코드 복사
Unexpected non-whitespace character after JSON
🔍 문제 해결 과정
claude_desktop_config.json 파일이 실제로는 .json.txt

Windows 기본 설정으로 파일 확장자가 숨겨져 있었음

파일 확장자 표시 활성화 후 정확한 파일명으로 수정

UTF-8 (BOM 없음)으로 재저장

✅ 결과
Claude Desktop에서 MCP 서버 정상 인식

4️⃣ STDIO MCP 서버 로그 출력으로 인한 통신 오류
❗ 문제
text
코드 복사
Unexpected token '📘', "Swagger"... is not valid JSON
🔍 문제 해결 과정
MCP는 STDOUT을 JSON 통신 채널로 사용

console.log()가 STDOUT을 오염시킴

모든 로그를 console.error()로 변경

js
코드 복사
console.error("로그 출력");
✅ 결과
MCP JSON 파싱 오류 해결

Claude ↔ MCP 통신 안정화

5️⃣ Swagger API가 MCP Tool로 등록되지 않음
❗ 문제
/v3/api-docs는 정상 접근 가능

Claude에서 API Tool 목록이 나타나지 않음

🔍 문제 해결 과정
MCP 서버 재시작 시 Swagger 로딩이 누락됨

서버 시작 시 Swagger 자동 로딩 로직 추가

js
코드 복사
await loadSwagger(AUTO_SWAGGER_URL);
✅ 결과
MCP 서버 시작과 동시에 API Tool 자동 등록

6️⃣ Spring Boot 포트 충돌 발생
❗ 문제
text
코드 복사
Port 8081 was already in use
🔍 문제 해결 과정
IntelliJ 실행 서버와 JAR 실행 서버를 동시에 기동

하나의 서버만 실행하도록 정리

dev / prod 환경별 포트 분리

✅ 결과
포트 충돌 제거

환경 분리 구조 확립

7️⃣ CORS 문제로 MCP 서버에서 API 호출 실패
❗ 문제
MCP 서버(Node.js)에서 Spring Boot API 호출 실패

🔍 문제 해결 과정
Spring Boot에 CORS 설정이 없었음

전역 CORS 설정 추가

java
코드 복사
registry.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("*");
✅ 결과
MCP 서버에서 API 정상 호출 가능

8️⃣ POST / PUT API requestBody 전달 실패
❗ 문제
POST API 호출 시 body 값이 전달되지 않음

🔍 문제 해결 과정
Swagger의 requestBody 구조를 처리하지 않았음

MCP Tool 생성 시 body 파라미터 추가

js
코드 복사
params["body"] = z.string().describe("JSON request body");
✅ 결과
POST / PUT API 정상 호출 가능

9️⃣ Claude가 HTTP를 직접 호출한다고 오해
❗ 문제
Claude가 URL을 직접 호출한다고 오해

🔍 문제 해결 과정
Claude는 HTTP를 직접 호출하지 않음

Claude → Tool 호출

MCP 서버 → fetch() 수행 구조로 역할 재정립

✅ 결과
Claude / MCP / Spring Boot 역할 분리 명확화

🔟 Spring Boot에서 MCP 서버를 직접 구현하려다 혼선 발생
❗ 문제
Spring Boot에서 SSE 기반 MCP 서버 구현 시도

🔍 문제 해결 과정
MCP는 STDIO 기반이 더 적합

Spring Boot는 API 서버 역할에 집중

MCP 서버를 Node.js로 분리

✅ 결과
아키텍처 단순화

유지보수성 향상

1️⃣1️⃣ Swagger 기반 Tool 이름 충돌 가능성
❗ 문제
/users/{id} 등 path 기반 Tool 이름 중복 위험

🔍 문제 해결 과정
HTTP method + path 기반으로 Tool 이름 생성

js
코드 복사
const toolName = `${method}_${path.replace(/[\/{}]/g, "_")}`;
✅ 결과
Tool 이름 유니크 보장

1️⃣2️⃣ 운영 환경에서 MCP 서버 노출 위험
❗ 문제
MCP 서버가 운영 환경에서 실행될 가능성

🔍 문제 해결 과정
dev / prod 프로파일 분리

MCP 서버는 dev 환경에서만 사용

yaml
코드 복사
spring:
  profiles:
    active: dev
✅ 결과
운영 환경 안정성 확보

보안 리스크 제거

🧠 종합 정리
MCP는 STDIO 기반 → 로그 출력에 매우 민감

Swagger는 문서이며 Tool 변환 로직이 핵심

Node.js와 Spring Boot의 역할 분리가 중요

실행 순서(Spring → MCP → Claude)가 시스템 안정성에 결정적
