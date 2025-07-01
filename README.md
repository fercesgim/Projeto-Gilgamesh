
## 📄 Documentação Automática

### src/main/java/gilgamesh/GilgameshApplication.java
A classe `GilgameshApplication` ☕ é a classe principal da aplicação Spring Boot.  Suas responsabilidades são:

* **Inicializar a aplicação:** 🌱 O método `main` utiliza `SpringApplication.run` para iniciar o servidor Spring Boot,  iniciando assim toda a aplicação.

* **Definir metadados OpenAPI:** 📚 A anotação `@OpenAPIDefinition` configura a geração automática da documentação da API usando Swagger, fornecendo informações como título ("Gilgamesh API"), versão ("1.0.0") e descrição ("API para documentação automática de código").

Em resumo, esta classe é o ponto de entrada da aplicação, responsável por iniciar o servidor e configurar a documentação da API. 🎉

### src/main/java/gilgamesh/controller/DocumentationController.java
## Classe `DocumentationController`

A classe `DocumentationController` 📄 é um controlador Spring que gerencia endpoints para gerar e comitar documentação de repositórios GitHub.  Ela atua como uma interface entre a aplicação e o serviço de documentação.

**Métodos Principais:**

* **`generateDocumentation`**: ✍️ Este método gera a documentação de um repositório GitHub. Recebe uma requisição com informações do repositório (usuário, nome do repositório e token do GitHub) e retorna o conteúdo da documentação em formato Markdown.  Ele utiliza internamente o `DocumentationService` para realizar a geração da documentação, que envolve a busca de arquivos Java, a solicitação de resumos via Google Gemini e a compilação dos resultados.  Em caso de erro, retorna um código de erro 500.

* **`commitReadme`**: 💾 Este método realiza o commit da documentação gerada (em formato Markdown) para um arquivo `README.md` no repositório GitHub especificado.  Ele utiliza o serviço `GitHubCommitter` para interagir com a API do GitHub, criando ou atualizando o arquivo e realizando o commit com uma mensagem padrão. Se ocorrer algum problema durante o commit (ex: erro de conexão com o GitHub), retorna um código de erro 500.


Em resumo, a classe `DocumentationController` oferece dois endpoints essenciais para a geração e atualização da documentação de um projeto, simplificando a integração com o GitHub e o processo de documentação. 🎉

### src/main/java/gilgamesh/dto/CommitRequest.java
A classe `CommitRequest` 📄 representa uma requisição para commit em um repositório GitHub.  Ela encapsula as informações necessárias para realizar essa operação.

**Responsabilidades:**  Armazenar os dados da requisição de commit.

**Métodos Principais:**

* `getUsername()`: 👤 Retorna o nome de usuário do GitHub.
* `setUsername(String username)`: ✍️ Define o nome de usuário do GitHub.
* `getRepositoryName()`: 📁 Retorna o nome do repositório.
* `setRepositoryName(String repositoryName)`: 📁 Define o nome do repositório.
* `getGithubToken()`: 🔑 Retorna o token de acesso do GitHub.
* `setGithubToken(String githubToken)`: 🔑 Define o token de acesso do GitHub.
* `getMarkdownContent()`: 📝 Retorna o conteúdo do commit em Markdown.
* `setMarkdownContent(String markdownContent)`: 📝 Define o conteúdo do commit em Markdown.

### src/main/java/gilgamesh/dto/DocumentationRequest.java
A classe `DocumentationRequest` 📄 representa uma requisição para geração de documentação.  Ela encapsula os dados necessários para este processo.

**Responsabilidades:**

* Armazenar informações de uma requisição de documentação. 🤔

**Métodos Principais:**

* **`getUsername()` 👤:** Retorna o nome de usuário.
* **`setUsername(String username)` ✍️:** Define o nome de usuário.
* **`getRepositoryName()` 📁:** Retorna o nome do repositório.
* **`setRepositoryName(String repositoryName)` ✍️:** Define o nome do repositório.
* **`getGithubToken()` 🔑:** Retorna o token do GitHub.
* **`setGithubToken(String githubToken)` ✍️:** Define o token do GitHub.

### src/main/java/gilgamesh/service/CodeDocumenterService.java
A classe `CodeDocumenterService` ✍️  é responsável por gerar sumários descritivos de códigos Java utilizando a API do Google Gemini.

**Métodos Principais:**

* **`CodeDocumenterService(String geminiApiKey)` 🔑:** Construtor da classe. Recebe a chave de API do Google Gemini como argumento e a utiliza para criar uma instância do modelo `GoogleAiGeminiChatModel`.  Configurando assim a conexão com a API.

* **`summarizeFilesIndividually(Map<String, String> fileContentsByPath)` 📚:** Este método recebe um mapa onde a chave é o caminho do arquivo e o valor é o conteúdo do código Java.  Para cada arquivo, ele gera um prompt para o modelo Gemini que inclui o caminho do arquivo e o código. O Gemini então retorna um resumo em português claro e objetivo com emoticons, que é armazenado em um mapa de retorno.  Este método itera sobre cada arquivo, solicitando um sumário individual para cada um. 😄

### src/main/java/gilgamesh/service/DocumentationService.java
A classe `DocumentationService` 📖 é responsável por gerar a documentação automática para repositórios GitHub.  Ela utiliza uma API externa (Gemini) para resumir o código Java encontrado.

**Métodos Principais:**

* **`generateDocumentationForRepository(String username, String repoName, String githubToken)`** 💻: Este método é o coração da classe. Ele recebe o nome de usuário, nome do repositório e um token do GitHub como entrada.  Ele:
    1. Busca todos os arquivos Java do repositório usando um `GitHubCodeFetcher`. 🎣
    2. Envia esses arquivos para um `CodeDocumenterService` para gerar resumos individuais de cada arquivo. 📝
    3. Constrói uma seção README contendo os resumos gerados. 📄
    4. Retorna a seção README como uma String.


* **`buildReadmeSection(Map<String, String> summaries)`** 🧱:  Este método privado constrói a seção README formatada a partir de um mapa contendo os nomes dos arquivos e seus respectivos resumos.  Ele formata o texto usando Markdown para melhor legibilidade.


Em resumo, a classe automatiza a geração de documentação, buscando código, resumindo-o e formatando-o para inclusão em um arquivo README.  🎉

### src/main/java/gilgamesh/service/GitHubCodeFetcher.java
## Documentação da Classe `GitHubCodeFetcher`

Esta classe 📁 se responsabiliza por buscar código Java de um repositório GitHub.  Ela utiliza a biblioteca `org.kohsuke.github` para interagir com a API do GitHub.

**Métodos Principais:**

* **`GitHubCodeFetcher(String token, String owner, String repoName)` 🗝️:** Construtor da classe.  Recebe o token de acesso do GitHub, o nome do dono do repositório e o nome do repositório como parâmetros.  Realiza a autenticação no GitHub e recupera o objeto `GHRepository` correspondente.  Lança `IllegalArgumentException` se o token for inválido e `IOException` caso ocorra algum erro de conexão.  Define também um timeout para a conexão e leitura.

* **`fetchAllJavaFiles(String owner, String repoName)` 📄:** Busca recursivamente todos os arquivos Java (.java) dentro de um repositório especificado. Retorna um `Map` onde a chave é o caminho do arquivo e o valor é o conteúdo do arquivo.  Lança `IOException` em caso de erros.

* **`fetchJavaFilesRecursive(List<GHContent> contents, Map<String, String> javaFiles)` 🔄:** Método recursivo auxiliar utilizado por `fetchAllJavaFiles`. Percorre a lista de conteúdo do repositório, identificando arquivos Java e adicionando-os ao mapa.  Processa subdiretórios recursivamente.  Lança `IOException` em caso de erros.


Em resumo, a classe simplifica o processo de obtenção de código-fonte Java de um repositório GitHub, fornecendo uma interface concisa e robusta. 🎉

### src/main/java/gilgamesh/service/GitHubCommitter.java
## Classe `GitHubCommitter`: Documentação

A classe `GitHubCommitter` 🐙 facilita a interação com um repositório GitHub para criar ou atualizar o arquivo `README.md`.  Ela encapsula a lógica de comunicação com a API do GitHub, simplificando o processo para desenvolvedores.

**Métodos Principais:**

* **`GitHubCommitter(String githubToken, String owner, String repoName)` 🏗️**: Construtor da classe. Recebe o token de acesso ao GitHub, o nome do dono do repositório e o nome do repositório para estabelecer a conexão.  Lança `IOException` caso haja problemas de conexão.

* **`createOrUpdateReadme(Map<String, String> fileSummaries)` ⚠️ (DEPRECATED)**: Método **depreciado** que cria ou atualiza a seção de documentação automática dentro do `README.md`.  A lógica foi movida para outra classe. Mantido apenas para referência.

* **`commitReadme(String readmeContent, String commitMessage)` 📝**:  Este é o método principal para criar ou atualizar o arquivo `README.md`. Recebe o conteúdo completo do `README.md` e a mensagem do commit como parâmetros.  Trata a criação e atualização do arquivo, imprimindo mensagens informativas no console.  Lança `IOException` em caso de erro.


* **`buildReadmeSection(Map<String, String> summaries)` 🧱**: Método privado auxiliar que constrói a seção de documentação automática para o `README.md` a partir de um mapa de resumos.

* **`updateAutoDocSection(String currentContent, String newSection)` 🔄**: Método privado auxiliar que atualiza uma seção específica do `README.md` com novo conteúdo.  Se a seção não existir, anexa o novo conteúdo ao final do arquivo.


Em resumo, a classe oferece uma maneira conveniente e robusta de gerenciar o conteúdo do `README.md` de um repositório GitHub. O método `commitReadme` é a principal ferramenta para essa tarefa, enquanto os métodos auxiliares garantem a correta formatação e atualização do arquivo.


