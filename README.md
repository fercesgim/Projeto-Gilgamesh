
## 📄 Documentação Automática

### src/main/java/gilgamesh/GilgameshApplication.java
A classe `GilgameshApplication` é a classe principal de uma aplicação Spring Boot.  Sua principal responsabilidade é iniciar a aplicação. O método `main` é o ponto de entrada da aplicação, responsável por executar o método `run` da classe `SpringApplication`, que inicializa todo o contexto da aplicação Spring Boot.  Não possui outros métodos além do `main`.

### src/main/java/gilgamesh/controller/DocumentationController.java
A classe `DocumentationController` é um controlador Spring REST que expõe dois endpoints para gerenciar a documentação de repositórios GitHub.

**Responsabilidades:**

* Receber requisições relacionadas à geração e ao commit de documentação (README).
* Delegar a lógica de negócio para os serviços `DocumentationService` e `GitHubCommitter`.
* Retornar respostas HTTP apropriadas, incluindo tratamento de erros.

**Métodos principais:**

* **`generateDocumentation`**:  Recebe uma requisição (`DocumentationRequest`) contendo informações do repositório GitHub (nome de usuário, nome do repositório e token de acesso).  Delega a geração da documentação para o `DocumentationService` e retorna o conteúdo gerado como resposta.  Trata exceções retornando uma mensagem de erro.

* **`commitReadme`**: Recebe uma requisição (`CommitRequest`) contendo informações do repositório e o conteúdo Markdown para o README.  Utiliza a classe `GitHubCommitter` para commitar o conteúdo atualizado no repositório GitHub. Retorna uma mensagem de sucesso ou uma mensagem de erro em caso de falha na operação de commit, incluindo detalhes da exceção.

### src/main/java/gilgamesh/dto/CommitRequest.java
A classe `CommitRequest` representa uma requisição para realizar um commit em um repositório GitHub.  Ela serve como um objeto de transferência de dados (DTO) contendo informações necessárias para a operação de commit.

Seus principais métodos são os getters e setters para os seguintes atributos:

* **`username` (String):** O nome de usuário do GitHub.
* **`repositoryName` (String):** O nome do repositório GitHub.
* **`githubToken` (String):** O token de acesso do GitHub.
* **`markdownContent` (String):** O conteúdo do commit em formato Markdown.

Em resumo, a classe encapsula os dados necessários para um commit no GitHub, facilitando o transporte e manipulação dessas informações.

### src/main/java/gilgamesh/dto/DocumentationRequest.java
A classe `DocumentationRequest` serve como um objeto de transferência de dados (DTO) que encapsula informações necessárias para uma requisição de documentação.  Ela contém os atributos `username`, `repositoryName` e `githubToken`, representando respectivamente o nome de usuário do GitHub, o nome do repositório e o token de acesso do GitHub.  Os métodos públicos são getters e setters para cada um desses atributos, permitindo acesso e modificação dos dados.  Em resumo, a classe facilita a passagem de informações relevantes para um serviço ou método responsável por gerar a documentação de um repositório GitHub.

### src/main/java/gilgamesh/service/CodeDocumenterService.java
A classe `CodeDocumenterService` é responsável por gerar sumários em português para arquivos de código Java, utilizando a API do Google Gemini.

**Métodos principais:**

* **`CodeDocumenterService(String geminiApiKey)`:** Construtor da classe. Recebe como argumento a chave de API do Google Gemini e instancia um objeto `ChatModel` para interagir com o modelo Gemini.

* **`summarizeFilesIndividually(Map<String, String> fileContentsByPath)`:** Este método recebe um mapa onde a chave é o caminho do arquivo e o valor é o conteúdo do arquivo Java. Para cada arquivo, ele gera um prompt para o modelo Gemini, contendo o código e a instrução para gerar um resumo em português das responsabilidades da classe e seus métodos principais. O resultado (o resumo gerado pelo Gemini) é armazenado em um mapa, onde a chave é o nome do arquivo e o valor é o resumo.  O método retorna este mapa com os sumários.

### src/main/java/gilgamesh/service/DocumentationService.java
A classe `DocumentationService` é responsável por gerar a documentação de um repositório GitHub contendo código Java.  Ela utiliza uma chave de API Gemini (`geminiApiKey`) e um token do GitHub para acessar e processar o código.

**Métodos principais:**

* **`generateDocumentationForRepository(String username, String repoName, String githubToken)`:** Este método é o principal da classe. Recebe o nome de usuário, nome do repositório e um token de acesso do GitHub como entrada.  Ele busca todos os arquivos Java do repositório, usando a classe `GitHubCodeFetcher`, e então gera um resumo para cada arquivo utilizando a classe `CodeDocumenterService`. Por fim, ele constrói uma seção de README contendo esses resumos.  Lança uma exceção `IOException` caso ocorra algum problema de entrada/saída.

* **`buildReadmeSection(Map<String, String> summaries)`:** Método privado que recebe um mapa de nomes de arquivos e seus respectivos resumos. Ele formata esses dados em uma seção de texto Markdown para inclusão em um arquivo README.

### src/main/java/gilgamesh/service/GitHubCodeFetcher.java
A classe `GitHubCodeFetcher` é responsável por buscar arquivos Java de um repositório GitHub.

**Responsabilidades principais:**

* **Conectar ao GitHub:** Estabelece uma conexão com a API do GitHub usando um token de acesso fornecido na construção do objeto.  Inclui tratamento de exceções para token inválido e configuração de timeout para requisições HTTP.

* **Buscar arquivos Java:**  O método `fetchAllJavaFiles` busca recursivamente todos os arquivos `.java` dentro de um repositório especificado (por nome de dono e repositório) e retorna um mapa onde a chave é o caminho do arquivo e o valor é o conteúdo do arquivo.

* **Busca recursiva:** A lógica de busca recursiva é encapsulada no método privado `fetchJavaFilesRecursive`, percorrendo diretórios e subdiretórios para encontrar todos os arquivos Java.

**Métodos principais:**

* **`GitHubCodeFetcher(String token, String owner, String repoName)`:** Construtor da classe. Recebe o token de acesso do GitHub, o nome do dono do repositório e o nome do repositório como parâmetros. Lança uma exceção `IllegalArgumentException` se o token for inválido e `IOException` caso ocorra algum erro de conexão.

* **`fetchAllJavaFiles(String owner, String repoName)`:** Busca e retorna todos os arquivos Java de um repositório especificado. Retorna um `Map<String, String>` contendo o caminho do arquivo e seu conteúdo. Lança `IOException` caso ocorram erros durante a busca.


Em resumo, a classe simplifica o processo de extração de código Java de repositórios GitHub, fornecendo uma interface concisa e robusta para essa tarefa.

### src/main/java/gilgamesh/service/GitHubCommitter.java
A classe `GitHubCommitter` é responsável por interagir com a API do GitHub para criar ou atualizar o arquivo `README.md` de um repositório.  Ela utiliza a biblioteca `org.kohsuke.github`.

**Métodos principais:**

* **`GitHubCommitter(String githubToken, String owner, String repoName)`:** Construtor da classe. Recebe o token de acesso do GitHub, o nome do dono do repositório e o nome do repositório como parâmetros.  Ele utiliza esses dados para estabelecer uma conexão com o repositório especificado. Lança `IOException` caso ocorra algum erro na conexão.

* **`createOrUpdateReadme(Map<String, String> fileSummaries)`:** (**Deprecado**) Este método, apesar de deprecado,  cria ou atualiza o arquivo `README.md` com uma seção de documentação automática gerada a partir de um mapa de resumos de arquivos.  A lógica deste método foi movida para outra classe.

* **`commitReadme(String readmeContent, String commitMessage)`:**  Este método cria ou atualiza o arquivo `README.md` com o conteúdo de uma string fornecida como parâmetro. Ele recebe o conteúdo completo do `README.md` e uma mensagem de commit.  Se o arquivo já existir, ele o atualiza; caso contrário, ele o cria.  Lança `IOException` caso ocorra algum problema na comunicação com o GitHub.

**Métodos auxiliares (privados):**

* **`buildReadmeSection(Map<String, String> summaries)`:** Constrói a seção de documentação automática para o `README.md` a partir de um mapa de resumos.



