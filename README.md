
## 📄 Documentação Automática

### src/main/java/gilgamesh/GilgameshApplication.java
## Documentação da Classe `GilgameshApplication`

**Resumo:** 🎉 Esta classe é o ponto de entrada principal da aplicação Spring Boot chamada "Gilgamesh".  Ela é bem simples, com a única responsabilidade de iniciar a aplicação. 🚀

**Métodos Principais:**

* **`main(String[] args)`:** ⚙️ Este método `static` é o coração da aplicação. Ele utiliza a classe `SpringApplication` para iniciar o contexto da aplicação Spring Boot, recebendo os argumentos de linha de comando (`args`) como entrada. É aqui que tudo começa! 


Em resumo, a classe `GilgameshApplication` é um arquivo pequeno porém crucial para o funcionamento da aplicação Spring Boot "Gilgamesh".  Sua simplicidade facilita a manutenção e compreensão do projeto. 👍

### src/main/java/gilgamesh/controller/DocumentationController.java
## Classe `DocumentationController` - Documentação de código Java

🎉 Esta classe atua como controladora REST para gerenciar a geração e o envio de documentação. Ela utiliza o Spring Framework para lidar com requisições HTTP. 🎉

**Responsabilidades Principais:**

* 📖 Gerar documentação para repositórios GitHub.
* 💾 Comitar a documentação gerada (geralmente um README) no repositório GitHub.

**Métodos Principais:**

* **`generateDocumentation(DocumentationRequest request)`:** ✍️ Este método recebe uma requisição contendo informações do repositório (usuário, nome do repositório e token de acesso do GitHub) e usa o `DocumentationService` para gerar a documentação. Retorna a documentação gerada como uma string, ou um erro caso ocorra algum problema.  Utiliza o método `generateDocumentationForRepository` do serviço.

* **`commitReadme(CommitRequest request)`:** ⬆️ Este método recebe uma requisição contendo informações do repositório e o conteúdo Markdown do README a ser comitado.  Ele cria um objeto `GitHubCommitter` para realizar o commit no GitHub. Retorna uma mensagem de sucesso ou um erro caso o commit falhe.


**Em resumo:**  A classe `DocumentationController` fornece uma interface para os serviços que gerenciam a geração e o envio da documentação, abstraindo a lógica de acesso ao GitHub e tratamento de erros para a camada de apresentação (frontend).  Ela garante uma comunicação limpa e eficiente entre o frontend e os serviços de back-end.

### src/main/java/gilgamesh/dto/CommitRequest.java
## Documentação da Classe `CommitRequest`

A classe `CommitRequest` 📦 representa uma requisição para comitar (salvar) conteúdo em um repositório GitHub.  Ela encapsula os dados necessários para realizar essa operação. 😄

**Responsabilidades:**

* Armazenar informações essenciais para um commit no GitHub.
* Fornecer acesso aos dados através de métodos getters e setters.

**Métodos Principais:**

* **`getUsername()` 👤:** Retorna o nome de usuário do GitHub.
* **`setUsername(String username)` 👤:** Define o nome de usuário do GitHub.
* **`getRepositoryName()` 📁:** Retorna o nome do repositório GitHub.
* **`setRepositoryName(String repositoryName)` 📁:** Define o nome do repositório GitHub.
* **`getGithubToken()` 🔑:** Retorna o token de acesso ao GitHub.  **Cuidado!**  Não exponha este token em seu código de produção. ⚠️
* **`setGithubToken(String githubToken)` 🔑:** Define o token de acesso ao GitHub.
* **`getMarkdownContent()` 📝:** Retorna o conteúdo a ser comitado, em formato Markdown.
* **`setMarkdownContent(String markdownContent)` 📝:** Define o conteúdo a ser comitado, em formato Markdown.


Em resumo, esta classe facilita o envio de dados necessários para a criação de um novo commit em um repositório GitHub, mantendo a informação organizada e acessível. 👍

### src/main/java/gilgamesh/dto/DocumentationRequest.java
## Classe `DocumentationRequest` - DTO para requisição de documentação 📄

Esta classe representa uma requisição para gerar documentação.  Ela atua como um Data Transfer Object (DTO), transportando os dados necessários para a operação.  Think of it as a package for information! 📦

**Responsabilidades:**

* Armazenar as informações essenciais para uma requisição de geração de documentação.

**Métodos Principais:**

* **`getUsername()` 🧑‍💻:** Retorna o nome de usuário do GitHub.
* **`setUsername(String username)` ✍️:** Define o nome de usuário do GitHub.
* **`getRepositoryName()` 📁:** Retorna o nome do repositório GitHub.
* **`setRepositoryName(String repositoryName)` 📁:** Define o nome do repositório GitHub.
* **`getGithubToken()` 🔑:** Retorna o token de acesso do GitHub.
* **`setGithubToken(String githubToken)` 🔑:** Define o token de acesso do GitHub.


Em resumo, a classe `DocumentationRequest` facilita a passagem de informações cruciais (usuário, repositório e token) para o processo de geração da documentação, mantendo o código organizado e legível. ✨

### src/main/java/gilgamesh/service/CodeDocumenterService.java
## Documentação da Classe `CodeDocumenterService`

**Resumo:** 🎉 Esta classe utiliza o modelo de linguagem Google Gemini para gerar automaticamente sumários de código Java.  Ela recebe um mapa de arquivos (nome do arquivo e conteúdo) e retorna um mapa com os sumários de cada arquivo.  Essencialmente, ela automatiza a documentação de código!


**Métodos Principais:**

* **`CodeDocumenterService(String geminiApiKey)`**: 🔑 Construtor da classe. Recebe a chave de API do Google Gemini como parâmetro e configura a conexão com o modelo de linguagem.  Sem essa chave, nada funciona!

* **`summarizeFilesIndividually(Map<String, String> fileContentsByPath)`**: 📚 Este método é o coração da classe. Ele recebe um mapa onde a chave é o caminho do arquivo Java e o valor é o código-fonte. Para cada arquivo, ele:
    1. Monta uma *prompt* (solicitação) para o modelo Gemini, incluindo o nome do arquivo e o código. A prompt pede um resumo em português claro e objetivo.
    2. Envia a *prompt* para o modelo Gemini (`model.chat(prompt)`).
    3. Recebe o sumário gerado pelo modelo.
    4. Armazena o sumário em um mapa, usando o nome do arquivo como chave.
    5. Finalmente, retorna o mapa contendo os sumários de todos os arquivos.  🚀


Em resumo, a classe simplifica o processo de documentação, delegando a tarefa de gerar os sumários para a inteligência artificial do Google Gemini.  Muito prático! 👍

### src/main/java/gilgamesh/service/DocumentationService.java
## Classe `DocumentationService` - Resumo da Documentação 📝

Esta classe ⚙️ é responsável por gerar a documentação automática para repositórios GitHub contendo código Java.  Ela utiliza outras classes para buscar o código e gerar os resumos.

**Métodos Principais:**

* **`generateDocumentationForRepository(String username, String repoName, String githubToken)` 📚:** Este método é o coração da classe ❤️. Ele recebe o nome de usuário, nome do repositório e um token de acesso ao GitHub como entrada.  Faz o seguinte:
    * Busca todos os arquivos `.java` do repositório especificado no GitHub usando a classe `GitHubCodeFetcher`. 📁
    * Gera um resumo para cada arquivo Java encontrado usando a classe `CodeDocumenterService`.  💡
    * Constrói uma seção `README` formatada contendo os resumos gerados. 📄
    * Retorna a seção `README` como uma string.  ➡️
    * Lança uma exceção `IOException` caso ocorra algum problema durante a leitura dos arquivos. ⚠️

* **`buildReadmeSection(Map<String, String> summaries)` 📝:**  Este método auxiliar recebe um mapa de resumos (nome do arquivo e seu resumo) e formata esses resumos em uma seção Markdown para inclusão no arquivo `README`.  Ele adiciona títulos e formatação para melhor legibilidade.


Em resumo, a classe `DocumentationService` automatiza a geração de documentação concisa para projetos Java em GitHub, facilitando a compreensão do código. 👍

### src/main/java/gilgamesh/service/GitHubCodeFetcher.java
## Documentação da Classe `GitHubCodeFetcher`

**Resumo:** 🎉 A classe `GitHubCodeFetcher` é responsável por buscar e recuperar o código-fonte (arquivos .java) de um repositório GitHub.  Ela utiliza a biblioteca `org.kohsuke.github` para interagir com a API do GitHub.

**Métodos Principais:**

* **`GitHubCodeFetcher(String token, String owner, String repoName)` 🗝️:** Este é o construtor da classe. Ele recebe como parâmetros o token de acesso do GitHub, o nome do dono do repositório e o nome do repositório.  Cria uma conexão com a API do GitHub usando o token fornecido, configurando timeouts para conexão e leitura, e obtém uma referência ao repositório especificado.  Caso o token seja inválido, lança uma exceção `IllegalArgumentException`.

* **`fetchAllJavaFiles(String owner, String repoName)` 📚:** Este método busca recursivamente todos os arquivos Java (.java) dentro do repositório especificado (dono e nome do repositório são passados como parâmetros). Retorna um mapa onde a chave é o caminho do arquivo e o valor é o conteúdo do arquivo.  Utiliza o método recursivo `fetchJavaFilesRecursive` para percorrer a estrutura de diretórios do repositório.

* **`fetchJavaFilesRecursive(List<GHContent> contents, Map<String, String> javaFiles)` 🔄:**  Este é um método recursivo privado que percorre a lista de conteúdo de um diretório. Para cada item, verifica se é um arquivo .java. Se for, adiciona o caminho e o conteúdo ao mapa `javaFiles`. Se for um diretório, chama a si mesmo recursivamente para processar o conteúdo do subdiretório.


Em resumo, a classe facilita a obtenção de código-fonte Java de repositórios GitHub de forma eficiente e organizada.  ✅

### src/main/java/gilgamesh/service/GitHubCommitter.java
## Documentação da Classe `GitHubCommitter`

Esta classe 📁  `GitHubCommitter`  é responsável por interagir com o GitHub 🐙 para criar e atualizar o arquivo `README.md` de um repositório.  Ela utiliza a biblioteca `org.kohsuke.github` para se comunicar com a API do GitHub.


**Métodos Principais:**

* **`GitHubCommitter(String githubToken, String owner, String repoName)` 🏗️:** Construtor da classe. Recebe o token de acesso ao GitHub, o nome do dono do repositório e o nome do repositório como parâmetros.  Cria uma conexão com o repositório especificado.  Pode lançar uma exceção `IOException` caso ocorra algum erro na conexão.

* **`createOrUpdateReadme(Map<String, String> fileSummaries)` ⚠️ (DEPRECATED):**  Este método está depreciado. Sua funcionalidade foi migrada para outra classe.  Ele atualiza ou cria o arquivo `README.md` com uma seção de documentação gerada a partir de um mapa de resumos de arquivos.

* **`commitReadme(String readmeContent, String commitMessage)` 📝:**  Este método cria ou atualiza o arquivo `README.md` com o conteúdo fornecido. Recebe como parâmetros o conteúdo completo do `README.md` e a mensagem do commit. Gerencia a criação ou atualização do arquivo, exibindo mensagens informativas no console.  Pode lançar uma exceção `IOException` caso haja problemas na comunicação com o GitHub.


* **`buildReadmeSection(Map<String, String> summaries)` 🧱:** Método privado que constrói a seção de documentação automática para o `README.md` a partir de um mapa de resumos.

* **`updateAutoDocSection(String currentContent, String newSection)` ✏️:** Método privado que atualiza a seção de documentação automática existente no `README.md` ou a adiciona caso ela não exista.


Em resumo, a classe simplifica a tarefa de gerenciar o conteúdo do arquivo `README.md` de um repositório GitHub, permitindo a atualização ou criação do mesmo de forma eficiente e controlada. 🎉


