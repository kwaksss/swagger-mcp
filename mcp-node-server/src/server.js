import { McpServer } from "@modelcontextprotocol/sdk/server/mcp.js";
import { StdioServerTransport } from "@modelcontextprotocol/sdk/server/stdio.js";
import { z } from "zod";

// ─────────────────────────────────────────────
//  MCP 서버 생성입니다요
// ─────────────────────────────────────────────
const server = new McpServer({
  name: "swagger-mcp",
  version: "1.0.0",
});

// ─────────────────────────────────────────────
//  기본 테스트용 Tool (hello)
// ─────────────────────────────────────────────
server.tool(
  "hello",
  "테스트용: 서버가 정상 작동하는지 확인",
  {
    name: z.string().describe("Your name"),
  },
  async ({ name }) => {
    return {
      content: [
        {
          type: "text",
          text: `안녕 ${name}! Node.js MCP 서버가 정상 동작 중이야 `,
        },
      ],
    };
  }
);

// ─────────────────────────────────────────────
// Swagger → MCP Tool 자동 등록 함수
// ─────────────────────────────────────────────
async function registerSwaggerTools(server, swaggerJson) {
  const paths = swaggerJson.paths || {};
  const baseUrl = swaggerJson.servers?.[0]?.url || "";

  for (const [path, methods] of Object.entries(paths)) {
    for (const [method, details] of Object.entries(methods)) {
      const toolName = `${method.toLowerCase()}_${path.replace(/[\/{}]/g, "_")}`;

      const params = {};
      if (details.parameters) {
        for (const p of details.parameters) {
          params[p.name] = z.string().describe(
            `(${p.in}) ${p.description || p.name}`
          );
        }
      }

      const hasRequestBody = details.requestBody?.content?.["application/json"];
      if (hasRequestBody) {
        params["body"] = z.string().describe("JSON request body 문자열");
      }

      server.tool(
        toolName,
        `Swagger API: [${method.toUpperCase()}] ${path}`,
        params,
        async (input) => {
          const resolvedUrl = path.replace(/{(.*?)}/g, (_, key) => input[key]);
          const fullUrl = baseUrl + resolvedUrl;

          try {
            const options = {
              method: method.toUpperCase(),
              headers: { "Content-Type": "application/json" }
            };

            if (input.body) {
              options.body = input.body;
            }

            const res = await fetch(fullUrl, options);
            const data = await res.text();

            return {
              content: [
                {
                  type: "text",
                  text:
`✔ API 호출 성공!
URL: ${fullUrl}

응답:
${data}`
                }
              ]
            };
          } catch (err) {
            return {
              content: [
                { type: "text", text: ` 호출 실패: ${err.message}` }
              ]
            };
          }
        }
      );
    }
  }
}

// ─────────────────────────────────────────────
// Swagger 문서를 불러오는 Tool
// ─────────────────────────────────────────────
async function loadSwagger(url) {
  const response = await fetch(url);

  if (!response.ok) {
    console.error(`Swagger 문서 로드 실패 (HTTP ${response.status})`);
    return;
  }

  const swaggerJson = await response.json();

  await registerSwaggerTools(server, swaggerJson);

  console.error(` Swagger 자동 로드 완료: ${swaggerJson.info?.title}`);
}

server.tool(
  "load_swagger",
  "Swagger(OpenAPI) 문서를 불러와 API들을 MCP Tool로 변환합니다.",
  {
    url: z.string().describe("Swagger JSON URL"),
  },
  async ({ url }) => {
    try {
      await loadSwagger(url);

      return {
        content: [
          { type: "text", text: `Swagger 문서 로드 완료! ${url}` }
        ]
      };

    } catch (err) {
      return {
        content: [
          { type: "text", text: `오류 발생: ${err.message}` }
        ]
      };
    }
  }
);

// ─────────────────────────────────────────────
// 🚀 서버 시작 시 Swagger 자동 로딩
// ─────────────────────────────────────────────
const AUTO_SWAGGER_URL = "http://localhost:8081/v3/api-docs";

// ─────────────────────────────────────────────
// STDIO로 MCP 서버 실행
// ─────────────────────────────────────────────
async function main() {
  await loadSwagger(AUTO_SWAGGER_URL); // ← 자동 로딩!!

  const transport = new StdioServerTransport();
  await server.connect(transport);
  console.error("Swagger MCP 서버 실행 중... (STDIO mode)");
}

main().catch((err) => {
  console.error("Fatal error:", err);
  process.exit(1);
});
