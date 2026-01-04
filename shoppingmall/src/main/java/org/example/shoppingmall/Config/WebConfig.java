package org.example.shoppingmall.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//CORS 설정을 안해주면 MCP 서버 측에서 fetch로 데이터를 요청할때 보안상 막힐 수 있다.
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")      // 모든 경로에 대해
                .allowedOrigins("*")    // 모든 출처 허용 (개발 시)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}
