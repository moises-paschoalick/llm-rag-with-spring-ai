package com.gotocode.ai;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/*
@SpringBootApplication
public class AiApplicationOld {

	public static void main(String[] args) {
		SpringApplication.run(AiApplicationOld.class, args);
	}

	@Component
	static class CarinaAiClient {

		private final VectorStore vectorStore;
		private final ChatClient chatClient;

		CarinaAiClient(VectorStore vectorStore, ChatClient chatClient){
			this.vectorStore = vectorStore;
            this.chatClient = chatClient;
        }

		String chat (String message) {
			var prompt = """
					     
					Você está auxiliando com perguntas sobre a Onnibank.
					A Onnibank é É uma Conta Digital que oferece soluções inovadoras para sua empresa pagar o salário de seus colaboradores em tempo real e com tecnologia moderna.
					     
					Use as informações da seção DOCUMENTOS para fornecer respostas precisas, mas aja como se soubesse essas informações de forma inata.
					Se não tiver certeza, simplesmente diga que não sabe.
					     
					DOCUMENTOS:
					{documents}
					
					""";

			var listOfSimilarDocs = this.vectorStore.similaritySearch(message);
			var docs = listOfSimilarDocs.stream()
					.map(Document::getContent)
					.collect(Collectors.joining(System.lineSeparator()));

			var systemMessage = new SystemPromptTemplate( prompt )
					.createMessage( Map.of ("documents", docs));

			var userMessage = new UserMessage(message);
			var promptList = new Prompt(List.of(systemMessage, userMessage));
			var aiResponse = this.chatClient.call(promptList);
			return aiResponse.getResult().getOutput().getContent();
		}
	}

	@Bean
	ApplicationRunner demo (
			VectorStore vectorStore,
			@Value("file://${HOME}/Desktop/DEV/SpringAI/pdfs/onnibank.pdf") Resource pdf,
			JdbcTemplate template,
			ChatClient chatClient,
			CarinaAiClient carinaAiClient
			) {
		return args -> {
			//setup(vectorStore, pdf, template);

			System.out.println(
					carinaAiClient.chat("""
							Quais transações posso fazer na Conta Digital Onnibank?
							""")
			);

		};

	}

	private static void setup(VectorStore vectorStore, Resource pdf, JdbcTemplate template) {
		template.update("delete from vector_store");

		var config = PdfDocumentReaderConfig
				.builder()
				.withPageExtractedTextFormatter(new ExtractedTextFormatter.Builder()
						.withNumberOfBottomTextLinesToDelete(3)
						.build())
				.build();

		var pdfReader = new PagePdfDocumentReader(pdf, config);

		var textSplitter = new TokenTextSplitter();

		var docs = textSplitter.apply(pdfReader.get());

		vectorStore.accept(docs);
	}

}
*/