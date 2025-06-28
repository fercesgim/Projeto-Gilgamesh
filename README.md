
## 📄 Documentação Automática

### src/main/java/gilgamesh/GilgameshApplication.java
## Documentação da Classe `GilgameshApplication`

Esta classe Java, representada pelo ⚙️,  é a aplicação principal do projeto Gilgamesh. Sua responsabilidade é iniciar a aplicação Spring Boot.  Ela possui apenas um método principal:


* **`main(String[] args)`**: 🚀 Este método é o ponto de entrada da aplicação. Ele utiliza a classe `SpringApplication` para iniciar o contexto Spring Boot, carregando todas as configurações e componentes necessários para a execução da aplicação Gilgamesh.  Os argumentos (`args`) são passados para a aplicação Spring Boot.

### src/main/java/gilgamesh/controller/DocumentationController.java
## Classe `DocumentationController` - Resumo

Esta classe 📄 é um controlador Spring REST que gerencia requisições relacionadas à geração e envio de documentação.  Ela atua como intermediária entre a interface de usuário (frontend) e os serviços que realizam o trabalho pesado.

**Métodos Principais:**

* **`generateDocumentation`**: ✍️ Este método recebe uma requisição (`DocumentationRequest`) contendo informações do repositório GitHub (usuário, nome do repositório e token). Ele utiliza o `DocumentationService` para gerar a documentação e retorna o conteúdo gerado como resposta (`ResponseEntity<String>`).  Trata possíveis exceções retornando mensagens de erro apropriadas.

* **`commitReadme`**: 💾 Este método recebe uma requisição (`CommitRequest`) contendo informações do repositório e o conteúdo Markdown do README.  Ele cria um objeto `GitHubCommitter` para enviar o conteúdo para o GitHub, realizando o commit do README atualizado.  Retorna uma mensagem de sucesso ou uma mensagem de erro em caso de falha (`IOException`).  


Em resumo, a classe `DocumentationController` facilita a geração e o envio de documentação para repositórios GitHub através de uma API REST. 🎉

### src/main/java/gilgamesh/dto/CommitRequest.java
## Classe `CommitRequest` - Resumo 📝

Esta classe Java, `CommitRequest`, representa uma requisição para um commit em um repositório GitHub.  Ela serve como um objeto de transferência de dados (DTO) para encapsular informações necessárias para a operação de commit.  😄

**Métodos Principais:**

* **`getUsername()` 👤:** Retorna o nome de usuário do GitHub.
* **`setUsername(String username)` ✍️:** Define o nome de usuário do GitHub.
* **`getRepositoryName()` 📁:** Retorna o nome do repositório.
* **`setRepositoryName(String repositoryName)` 📁:** Define o nome do repositório.
* **`getGithubToken()` 🔑:** Retorna o token de acesso do GitHub.
* **`setGithubToken(String githubToken)` 🔑:** Define o token de acesso do GitHub.
* **`getMarkdownContent()` 📄:** Retorna o conteúdo do commit em formato Markdown.
* **`setMarkdownContent(String markdownContent)` 📄:** Define o conteúdo do commit em formato Markdown.


Em resumo, a classe facilita o envio de dados para um serviço que realiza commits no GitHub, agrupando informações essenciais em um único objeto.  🚀

### src/main/java/gilgamesh/dto/DocumentationRequest.java
A classe `DocumentationRequest` 📄 representa uma requisição para geração de documentação.  Ela encapsula as informações necessárias para este processo.

**Responsabilidades:**

* Armazenar os dados de entrada para a geração da documentação. 🤔

**Métodos Principais:**

* `getUsername()`:  Retorna o nome de usuário do GitHub. 👤
* `setUsername(String username)`: Define o nome de usuário do GitHub. ✍️
* `getRepositoryName()`: Retorna o nome do repositório. 📁
* `setRepositoryName(String repositoryName)`: Define o nome do repositório. ✍️
* `getGithubToken()`: Retorna o token de acesso ao GitHub. 🔑
* `setGithubToken(String githubToken)`: Define o token de acesso ao GitHub. ✍️


Em resumo, a classe serve como um contêiner para os dados essenciais (usuário, repositório e token)  necessários para uma requisição de documentação.  ✅

### src/main/java/gilgamesh/service/CodeDocumenterService.java
## Documentação da Classe `CodeDocumenterService`

Esta classe 📄 utiliza o modelo de linguagem Google Gemini 🤖 para gerar sumários de código Java.  Ela recebe o código fonte como entrada e retorna um resumo descritivo, incluindo as responsabilidades da classe e seus métodos principais, em português e com emojis.

**Métodos Principais:**

* **`CodeDocumenterService(String geminiApiKey)` 🔑:**  Construtor da classe. Recebe a chave de API do Google Gemini para autenticação e inicializa o modelo.  🎉

* **`summarizeFilesIndividually(Map<String, String> fileContentsByPath)` 📚:** Este método é o coração da classe.  Ele recebe um mapa onde a chave é o caminho do arquivo e o valor é o conteúdo do código Java.  Para cada arquivo:
    * Lê o código Java. 💻
    * Monta uma mensagem (prompt) para o modelo Gemini, incluindo o nome do arquivo e o código. 📝
    * Envia o prompt ao modelo Gemini e recebe o resumo gerado. 🗣️
    * Armazena o resumo em um mapa, com o nome do arquivo como chave e o resumo como valor. 🗂️
    * Retorna um mapa contendo os sumários de todos os arquivos processados.  ✅

### src/main/java/gilgamesh/service/DocumentationService.java
A classe `DocumentationService` 📄 é responsável por gerar a seção de documentação automática para um repositório GitHub.  Ela utiliza informações do GitHub e uma API externa (Gemini) para gerar os resumos.

**Métodos Principais:**

* **`generateDocumentationForRepository(String username, String repoName, String githubToken)`** 💻: Este método é o coração da classe.  Ele recebe o nome de usuário, nome do repositório e um token de acesso ao GitHub.  Com essas informações, ele:
    * Busca todos os arquivos `.java` do repositório. 🔎
    * Envia esses arquivos para um serviço externo (`CodeDocumenterService`) para gerar resumos individuais.  🗣️
    * Constrói uma seção Markdown (`README.md`) contendo os resumos gerados. ✍️
    * Retorna a seção Markdown pronta para inclusão no README. ➡️

* **`buildReadmeSection(Map<String, String> summaries)`** 📝: Este método auxiliar formata os resumos gerados em uma seção Markdown legível, pronta para inserção em um arquivo `README`.  Ele itera sobre os resumos e os organiza com títulos e formatação Markdown.

### src/main/java/gilgamesh/service/GitHubCodeFetcher.java
A classe `GitHubCodeFetcher` 📁  é responsável por buscar código Java de um repositório GitHub.

**Métodos Principais:**

* **`GitHubCodeFetcher(String token, String owner, String repoName)` 🗝️:** Construtor da classe.  Recebe o token de acesso do GitHub, o nome do dono do repositório e o nome do repositório como parâmetros.  Valida o token e configura a conexão com o GitHub, incluindo timeouts para conexão e leitura.  Se o token for inválido, lança uma exceção.  Cria uma conexão com o repositório especificado.

* **`fetchAllJavaFiles(String owner, String repoName)` ⬇️:** Busca recursivamente todos os arquivos Java dentro de um repositório GitHub especificado. Retorna um mapa onde a chave é o caminho do arquivo e o valor é o conteúdo do arquivo.

* **`fetchJavaFilesRecursive(List<GHContent> contents, Map<String, String> javaFiles)` 🔄:** Método recursivo auxiliar que percorre a estrutura de diretórios do repositório, identificando e armazenando o conteúdo dos arquivos `.java`.  Processa cada item da lista `contents`, verificando se é um arquivo `.java` ou um diretório. Se for um diretório, chama a si mesmo recursivamente.

### src/main/java/gilgamesh/service/GitHubCommitter.java
## Classe `GitHubCommitter`

A classe `GitHubCommitter` 🐙  é responsável por interagir com um repositório GitHub para criar e atualizar o arquivo `README.md`.  Ela utiliza a biblioteca `org.kohsuke.github` para comunicação com a API do GitHub.

**Métodos Principais:**

* **`GitHubCommitter(String githubToken, String owner, String repoName)` 🏗️**: Construtor da classe. Recebe o token de acesso ao GitHub, o nome do dono do repositório e o nome do repositório, e estabelece a conexão com o repositório.  Lança `IOException` em caso de erro.

* **`createOrUpdateReadme(Map<String, String> fileSummaries)` ⚠️ (Deprecated)**:  Método **deprecated**.  Anteriormente, criava ou atualizava o `README.md` com uma seção de documentação automática gerada a partir de um mapa de resumos de arquivos. A lógica foi movida para outra classe.

* **`commitReadme(String readmeContent, String commitMessage)` 📝**: Cria ou atualiza o arquivo `README.md` com o conteúdo de uma string fornecida. Recebe o conteúdo completo do `README.md` e a mensagem do commit.  Lança `IOException` em caso de erro.  Este é o método recomendado para atualização do README.

**Métodos Auxiliares:**

* **`buildReadmeSection(Map<String, String> summaries)` 🧱**: Constrói uma seção de texto formatada para inclusão no `README.md`, a partir de um mapa de resumos.

* **`updateAutoDocSection(String currentContent, String newSection)` 🔄**: Atualiza uma seção específica dentro do conteúdo existente do `README.md`.  Este método tem lógica para lidar com a situação em que a seção não existe no arquivo original.


Em resumo, a classe simplifica a tarefa de gerenciar o arquivo `README.md` em um repositório GitHub, oferecendo um método claro e conciso para commits. O método antigo foi mantido apenas como referência.


