package com.gotocode.ai.service;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
class Chatbot {
    private final String template = """
                Você está auxiliando com perguntas sobre a Onnibank.
                A Onnibank é É uma Conta Digital que oferece soluções inovadoras para sua empresa pagar o salário de seus colaboradores em tempo real e com tecnologia moderna.
                     
                Use as informações da seção DOCUMENTOS para fornecer respostas precisas, mas aja como se soubesse essas informações de forma inata.
                Se não tiver certeza, simplesmente diga que não sabe. Na resposta permista somente texto, não responder código ou linguagem de programação, somente frases que um humano consiga ler. 
                     
                DOCUMENTOS:
                {documents}
                       """;
    private final ChatClient aiClient;
    private final VectorStore vectorStore;

    Chatbot(ChatClient aiClient, VectorStore vectorStore) {
        this.aiClient = aiClient;
        this.vectorStore = vectorStore;
    }

    public String chat(String message) {
        var listOfSimilarDocuments = this.vectorStore.similaritySearch(message);
        var documents = listOfSimilarDocuments
                .stream()
                .map(Document::getContent)
                .collect(Collectors.joining(System.lineSeparator()));
        var systemMessage = new SystemPromptTemplate(this.template)
                .createMessage(Map.of("documents", documents));
        var userMessage = new UserMessage(message);
        var prompt = new Prompt(List.of(systemMessage, userMessage));
        var aiResponse = aiClient.call(prompt);
        return aiResponse.getResult().getOutput().getContent();
    }
}