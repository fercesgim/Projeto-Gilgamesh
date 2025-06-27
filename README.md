
## 📄 Documentação Automática

### src/main/java/gilgamesh/GilgameshApplication.java
A classe `GilgameshApplication` é o ponto de entrada principal para um aplicativo Spring Boot.  Sua única responsabilidade é iniciar a aplicação. O método `main` utiliza a classe `SpringApplication` para executar o aplicativo, carregando todas as configurações e componentes definidos pela anotação `@SpringBootApplication`.  Em resumo, esta classe serve como o bootstrap da aplicação.

### src/main/java/gilgamesh/controller/DocumentationController.java
A classe `DocumentationController` é um controlador Spring que expõe dois endpoints para gerenciamento de documentação (README):

**Responsabilidades:**

* Receber requisições relacionadas à geração e ao envio de documentação para repositórios.
* Delegar a geração da documentação para a classe `DocumentationService`.
* Lidar com possíveis exceções durante a geração e o envio da documentação, retornando respostas HTTP apropriadas.
* Gerenciar o processo de commit do README atualizado no Github.

**Métodos Principais:**

* **`generateDocumentation(DocumentationRequest request)`:**  Este método recebe um objeto `DocumentationRequest` como entrada (provavelmente contendo informações sobre o repositório), gera a documentação utilizando o serviço `documentationService` e retorna o conteúdo do README gerado em formato String como resposta, encapsulado em um `ResponseEntity`.  Trata exceções de parâmetros inválidos e erros internos, retornando mensagens de erro adequadas.

* **`commitReadme(CommitRequest request)`:** Este método recebe um objeto `CommitRequest`  (contendo credenciais do GitHub e o conteúdo Markdown do README) como entrada. Ele cria uma instância de `GitHubCommitter`, utilizando as credenciais fornecidas na requisição, e utiliza esta instância para enviar o conteúdo do README para o repositório especificado. Retorna uma mensagem de sucesso ou um erro interno em caso de falha.  A mensagem de commit é fixa ("Docs: Atualiza README.md via API Gilgamesh").

### src/main/java/gilgamesh/dto/CommitRequest.java
A classe `CommitRequest` representa uma requisição para realizar um commit em um repositório GitHub.  Ela encapsula os dados necessários para essa operação.

**Responsabilidades:**

Armazenar e fornecer acesso aos dados de uma requisição de commit, incluindo:

* **username:** O nome de usuário do GitHub.
* **repositoryName:** O nome do repositório GitHub.
* **githubToken:** O token de acesso do GitHub.
* **markdownContent:** O conteúdo do commit em formato Markdown.


**Métodos Principais:**

A classe possui apenas getters e setters para cada um dos atributos, permitindo o acesso e a modificação dos dados da requisição.  Não há métodos com lógica de negócio específica dentro da classe.

### src/main/java/gilgamesh/dto/DocumentationRequest.java
A classe `DocumentationRequest` serve como um objeto de transferência de dados (DTO) que encapsula informações necessárias para uma requisição de documentação.  Ela possui três atributos principais: `username` (nome de usuário), `repositoryName` (nome do repositório) e `githubToken` (token do GitHub).  Os métodos públicos são apenas getters e setters para esses atributos, permitindo acesso e modificação dos dados.  Em resumo, a classe facilita a passagem organizada dessas informações para outras partes do sistema.

### src/main/java/gilgamesh/service/CodeDocumenterService.java
A classe `CodeDocumenterService` utiliza a API OpenAI para gerar documentação de código Java.  Seus métodos principais são:

* **`CodeDocumenterService(String openAiApiKey)`:** Construtor da classe. Recebe a chave de API do OpenAI e configura um modelo de chat `gpt-3.5-turbo` para gerar a documentação.

* **`generateDocumentation(List<String> javaFileContents)`:**  Recebe uma lista de strings, onde cada string representa o conteúdo de um arquivo Java.  Concatena todos os códigos em um único prompt e o envia para o modelo de linguagem, retornando a documentação gerada como uma única string. A documentação abrange todos os arquivos fornecidos.

* **`summarizeFilesIndividually(Map<String, String> fileContentsByPath)`:** Recebe um mapa onde a chave é o caminho do arquivo e o valor é o conteúdo do arquivo Java.  Para cada arquivo, gera um prompt contendo o nome do arquivo e seu conteúdo, solicitando um resumo da classe e seus métodos. Retorna um mapa onde a chave é o nome do arquivo e o valor é o resumo da documentação gerada individualmente para cada arquivo.

### src/main/java/gilgamesh/service/DocumentationService.java
A classe `DocumentationService` é responsável por gerar automaticamente a seção de documentação para um repositório, a ser inserida no arquivo `README.md`.  Ela utiliza a API OpenAI para gerar resumos de código Java.

**Métodos principais:**

* **`generateDocumentationForRepository(DocumentationRequest request)`:** Este método é o principal ponto de entrada. Ele recebe um objeto `DocumentationRequest` contendo informações de acesso ao GitHub (usuário, nome do repositório e token) e realiza as seguintes etapas:
    1. Baixa todos os arquivos Java do repositório especificado utilizando a classe `GitHubCodeFetcher`.
    2. Gera um resumo para cada arquivo Java usando a classe `CodeDocumenterService`, que por sua vez utiliza a API OpenAI.
    3. Constrói uma string formatada para inclusão no `README.md`, contendo os resumos gerados, usando o método `buildReadmeSection`.  Retorna esta string como resultado.  Lança uma exceção `IOException` caso haja algum problema na comunicação com o GitHub.

* **`buildReadmeSection(Map<String, String> summaries)`:**  Este método privado formata os resumos gerados (recebidos como um mapa onde a chave é o caminho do arquivo e o valor é o resumo) em uma string para ser adicionada à seção de documentação do arquivo `README.md`.  A saída inclui um cabeçalho e lista cada arquivo com seu respectivo resumo.

### src/main/java/gilgamesh/service/GitHubCodeFetcher.java
A classe `GitHubCodeFetcher` é responsável por buscar arquivos Java de um repositório GitHub.

**Responsabilidades:**

* Conecta-se à API do GitHub utilizando um token de acesso.  Trata erros de token inválido. Configura um tempo limite para conexão e leitura.
* Recupera um repositório específico do GitHub, dado o nome do dono e do repositório.
* Busca recursivamente todos os arquivos Java dentro do repositório.

**Métodos Principais:**

* **`GitHubCodeFetcher(String token, String owner, String repoName)`:** Construtor da classe. Recebe o token de acesso do GitHub, o nome do dono do repositório e o nome do repositório como parâmetros.  Lança uma exceção `IllegalArgumentException` se o token for inválido e `IOException` caso ocorra um erro de conexão.

* **`fetchAllJavaFiles(String owner, String repoName)`:** Busca todos os arquivos `.java` no repositório especificado (através dos parâmetros `owner` e `repoName`) e retorna um mapa onde a chave é o caminho do arquivo e o valor é o conteúdo do arquivo. Retorna um `LinkedHashMap` para preservar a ordem. Lança `IOException` caso ocorra um erro durante a busca.

* **`fetchJavaFilesRecursive(List<GHContent> contents, Map<String, String> javaFiles)`:** Método recursivo auxiliar que percorre a estrutura de diretórios do repositório, buscando e adicionando os arquivos Java encontrados ao mapa `javaFiles`.  Lança `IOException` caso ocorra um erro.

### src/main/java/gilgamesh/service/GitHubCommitter.java
A classe `GitHubCommitter` é responsável por interagir com a API do GitHub para criar ou atualizar o arquivo `README.md` de um repositório.  Ela utiliza a biblioteca `org.kohsuke.github` para essa comunicação.

**Métodos principais:**

* **`GitHubCommitter(String githubToken, String owner, String repoName)`:** Construtor da classe. Recebe o token de acesso do GitHub, o nome do dono do repositório e o nome do repositório como parâmetros.  Inicializa a conexão com o repositório especificado. Lança `IOException` em caso de erro.

* **`createOrUpdateReadme(Map<String, String> fileSummaries)`:**  **(DEPRECATED)** Método depreciado, mantido apenas para referência.  Sua funcionalidade foi movida para outro serviço.  Criava ou atualizava a seção de documentação automática no arquivo `README.md` baseado em um mapa de resumos de arquivos.

* **`commitReadme(String readmeContent, String commitMessage)`:**  Cria ou atualiza o arquivo `README.md` com o conteúdo de texto fornecido (`readmeContent`) e uma mensagem de commit (`commitMessage`).  Trata a criação e atualização do arquivo, lidando com a possibilidade de o arquivo já existir ou não. Lança `IOException` em caso de erro.

**Métodos auxiliares (privados):**

* **`buildReadmeSection(Map<String, String> summaries)`:** Constrói a seção de documentação automática para o `README.md` a partir de um mapa de resumos.

* **`updateAutoDocSection(String currentContent, String newSection)`:** Atualiza uma seção específica dentro do conteúdo existente do `README.md`.  Atualmente, não possui marcadores definidos, tornando sua funcionalidade limitada.


Em resumo, a classe simplifica a interação com o GitHub para gerenciar o conteúdo do arquivo `README.md`, oferecendo métodos para criar e atualizar seu conteúdo de forma controlada e robusta. O método `commitReadme` é o método principal e atualizado para essa funcionalidade.


aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa


