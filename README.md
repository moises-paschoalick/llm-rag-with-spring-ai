# LLM RAG com Spring AI

Este projeto é uma aplicação Java baseada em Spring Boot, que utiliza a biblioteca Spring AI para criar um chatbot capaz de responder perguntas sobre documentos fornecidos (PDF ou TXT), com armazenamento vetorial usando pgvector (PostgreSQL). O sistema é voltado para responder dúvidas sobre a Onnibank, uma conta digital, utilizando informações extraídas dos documentos presentes nas pastas `pdfs` e `txt`.

## O que o projeto faz

- Expõe uma API REST (`/chat/ask?question=...`) que recebe perguntas e retorna respostas baseadas no conteúdo dos documentos fornecidos.
- Utiliza IA (OpenAI via Spring AI) para gerar respostas contextuais.
- Indexa documentos PDF e TXT em um banco vetorial (pgvector/PostgreSQL) para busca semântica.
- Permite inicializar a base de conhecimento a partir de arquivos PDF ou TXT.
- Utiliza Docker Compose para subir o banco de dados PostgreSQL com extensão pgvector e o pgAdmin para administração.

## Estrutura de Pastas

- `src/main/java/com/gotocode/ai/`: Código-fonte principal (controllers, serviços, lógica de IA).
- `src/main/resources/`: Configurações da aplicação.
- `pdfs/`: Documentos PDF usados como fonte de conhecimento.
- `txt/`: Documentos TXT usados como fonte de conhecimento.
- `docker-compose.yml`: Sobe o banco PostgreSQL com pgvector e o pgAdmin.
- `pom.xml`: Gerenciamento de dependências Maven.

## Pré-requisitos

- Java 21+
- Maven 3.8+
- Docker e Docker Compose
- Chave de API da OpenAI

## Como rodar o projeto

1. **Clone o repositório e acesse a pasta do projeto:**
   ```bash
   git clone <repo-url>
   cd llm-rag-with-spring-ai
   ```

2. **Configure a chave da OpenAI:**
   - Defina a variável de ambiente `OPENAI_KEY` com sua chave de API.

3. **Suba o banco de dados e o pgAdmin:**
   ```bash
   docker-compose up -d
   ```

4. **Compile e rode a aplicação:**
   ```bash
   ./mvnw spring-boot:run
   ```

5. **Utilize a API:**
   - Envie perguntas para o endpoint:
     ```
     GET http://localhost:8080/chat/ask?question=Sua pergunta aqui
     ```

6. **(Opcional) Inicialize a base de conhecimento:**
   - O serviço pode ser configurado para carregar e indexar os documentos PDF ou TXT presentes nas pastas `pdfs/` e `txt/`.

## Observações

- O banco de dados é acessível em `localhost:5432` (usuário e senha: `postgres`).
- O pgAdmin estará disponível em `localhost:5050` (email padrão: `pgadmin4@pgadmin.org`, senha: `admin`).
- Os arquivos de exemplo já estão nas pastas `pdfs/onnibank.pdf` e `txt/arquivo.txt`. 