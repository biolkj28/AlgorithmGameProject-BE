package com.seventeam.algoritmgameproject.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(@Value("1.0") String version) {
        Info info = new Info().title("알고리즘 게임 배틀- 친다 프로젝트").version(version)
                .description("JDoodle,Stomp를 사용한 서버.\n\n" +
                        "STOMP 구독\n" +
                        "1. websocket 연결 주소: /ws-stomp\n" +
                        "2. ready 구독 주소: /topic/game/room/{roomId}\n" +
                        "3. 실시간 코드 전송 구독 주소: /user/queue/game/codeMessage\n" +
                        "4. 실시간 코드 전송: /app/game/codeMessage, 파라미터: {roomId:, sender:, message:}\n" +
                        "5. ready: /app/game/ready, 파라미터: 파라미터: {roomId:,server:, sender:}\n")
                .termsOfService("http://swagger.io/terms/")
                .contact(new Contact().name("lee").url("https://biolkj28.tistory.com/").email("biolkj28@gmail.com"))
                .license(new License().name("Apache License Version 2.0").url("http://www.apache.org/licenses/LICENSE-2.0"));

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
