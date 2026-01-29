package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.llm.GeminiClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(
        exclude = {
                DataSourceAutoConfiguration.class
        }
)
public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        SpringApplication app = new SpringApplication(Main.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        ConfigurableApplicationContext ctx = app.run(args);

        GeminiClient geminiClient = ctx.getBean(GeminiClient.class);
        geminiClient.chat("안녕 제미나이야.");
    }
}