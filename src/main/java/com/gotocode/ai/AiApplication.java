package com.gotocode.ai;

import com.gotocode.ai.service.ChatService;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class AiApplication {

	private Boolean upload = true;

	public static void main(String[] args) {
		SpringApplication.run(AiApplication.class, args);
	}

	@Bean
	VectorStore vectorStore(EmbeddingClient ec, JdbcTemplate t) {
		return new PgVectorStore(t, ec);
	}

	@Bean
	TokenTextSplitter tokenTextSplitter() {
		return new TokenTextSplitter();
	}

	@Bean
	ApplicationRunner applicationRunner(ChatService chatService) {
		return args -> {
			try {
				if(upload) chatService.init("TXT");
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	}

}
