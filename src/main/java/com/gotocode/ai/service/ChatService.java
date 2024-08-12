package com.gotocode.ai.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ChatService {

    private final Chatbot chatbot;
    private final VectorStore vectorStore;
    private final JdbcTemplate jdbcTemplate;

    @Value("file:/home/moises/Desktop/DEV/SpringAI/pdfs/onnibank.pdf")
    private Resource pdfResource;

    @Value("file:/home/moises/Desktop/DEV/SpringAI/txt/arquivo.txt")
    private Resource txtResource;

    public ChatService(Chatbot chatbot, VectorStore vectorStore, JdbcTemplate jdbcTemplate)  {
        this.chatbot = chatbot;
        this.vectorStore = vectorStore;
        this.jdbcTemplate = jdbcTemplate;
    }


    public void init(String fileType) throws Exception {
        System.out.println(">>> foi deletado os dados do banco vetorial");
        jdbcTemplate.update("delete from vector_store");
        System.out.println(">>> o tipo de arquivo carregado: " + fileType);

        List<Document> documents = new ArrayList<>();

        if(fileType.equals("PDF"))
            documents = addPdf();

        if(fileType.equals("TXT"))
            documents = addTxt();

        vectorStore.accept(documents);
    }

    public List<Document> addPdf() {
        var config = PdfDocumentReaderConfig.builder()
                .withPageExtractedTextFormatter(new ExtractedTextFormatter.Builder()
                        .withNumberOfBottomTextLinesToDelete(3)
                        .withNumberOfTopPagesToSkipBeforeDelete(1)
                        .build())
                .withPagesPerDocument(1)
                .build();

        var pdfReader = new PagePdfDocumentReader(pdfResource, config);
        var textSplitter = new TokenTextSplitter();
        var documents = textSplitter.apply(pdfReader.get());

        if (documents == null || documents.isEmpty()) {
            throw new IllegalStateException("Nenhum documento foi extraído do PDF.");
        }

        return documents;
    }

    public List<Document> addTxt() {
        TextReader textReader = new TextReader(txtResource);
        textReader.getCustomMetadata().put("filename", "arquivo.txt");
        var documents = textReader.get();
        return documents;

    }

    public String getResponse(String question) {
        return chatbot.chat(question);
    }

}
