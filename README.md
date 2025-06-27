
## 📄 Documentação Automática

### src/main/java/gilgamesh/GilgameshApplication.java
## Resumo de `gilgamesh.GilgameshApplication`

Este documento descreve as responsabilidades da classe `GilgameshApplication` e seus métodos principais.

---

### Responsabilidades da Classe `GilgameshApplication`

A classe `GilgameshApplication` atua como o **ponto de entrada principal** para a aplicação Spring Boot. Suas responsabilidades incluem:

1.  **Inicialização da Aplicação:** É a classe que inicia e configura todo o contexto da aplicação Spring Boot.
2.  **Habilitação do Spring Boot:** A anotação `@SpringBootApplication` indica ao Spring Boot que esta é a classe de configuração primária, habilitando a configuração automática, o escaneamento de componentes (`@ComponentScan`) e a definição de beans.
3.  **Bootstrapping:** Orquestra o processo de "bootstrapping" do Spring Boot, que envolve a configuração do ambiente, a criação do ApplicationContext e o registro de todos os beans necessários.

### Métodos Principais

#### `main(String[] args)`

*   **Responsabilidade:** Este é o **método principal (entry point) da aplicação Java**, conforme convenção padrão.
*   **Ação:** Sua única e crucial responsabilidade é invocar o método estático `SpringApplication.run()`. Este método é o coração do processo de inicialização do Spring Boot, responsável por carregar e configurar o ambiente Spring.
*   **Parâmetros:**
    *   `GilgameshApplication.class`: Informa ao Spring Boot qual é a classe principal de configuração (aquela anotada com `@SpringBootApplication`) que ele deve usar para iniciar a aplicação.
    *   `args`: Repassa quaisquer argumentos de linha de comando recebidos para o Spring Boot, que pode usá-los para configurar propriedades ou perfis.

### src/main/java/gilgamesh/controller/DocumentationController.java
Claro, aqui está o resumo do código Java fornecido:

---

### Resumo da Classe `DocumentationController`

**Arquivo:** `src/main/java/gilgamesh/controller/DocumentationController.java`

Esta classe atua como um **controlador REST** (`@RestController`) para a aplicação "Gilgamesh", especificamente responsável por gerenciar as requisições HTTP relacionadas à **geração e ao commit de documentação**. Ela expõe *endpoints* da API sob o caminho base `/api/documentation` e permite requisições de origem `http://localhost:3000`.

**Responsabilidades Principais da Classe:**

*   **Expor APIs para Documentação:** Oferece *endpoints* HTTP para que clientes externos possam interagir com as funcionalidades de documentação da aplicação.
*   **Orquestração de Requisições:** Atua como uma ponte entre as requisições HTTP recebidas e os serviços internos que executam a lógica de negócio (`DocumentationService` para geração e `GitHubCommitter` para commit).
*   **Tratamento de Requisições e Respostas:** Processa os dados recebidos nas requisições (`@RequestBody`), invoca a lógica apropriada e retorna respostas HTTP formatadas (sucesso, erro do cliente, erro interno do servidor).

---

### Métodos Principais

#### 1. `generateDocumentation(DocumentationRequest request)`

*   **Tipo de Requisição:** `POST` para o *endpoint* `/api/documentation/generate`.
*   **Responsabilidade:** Iniciar o processo de **geração de documentação** para um repositório.
*   **Funcionalidade:**
    *   Recebe uma requisição (`DocumentationRequest`) contendo os parâmetros necessários para a geração (ex: detalhes do repositório).
    *   Delega a lógica principal de geração de documentação para o `DocumentationService`.
    *   Retorna o conteúdo da documentação gerada (provavelmente um arquivo `README.md`) em caso de sucesso.
    *   Gerencia erros, respondendo com `400 Bad Request` para parâmetros inválidos ou `500 Internal Server Error` para falhas internas.

#### 2. `commitReadme(CommitRequest request)`

*   **Tipo de Requisição:** `POST` para o *endpoint* `/api/documentation/commit`.
*   **Responsabilidade:** Realizar o **commit de um arquivo README.md** (ou outro conteúdo Markdown) diretamente em um repositório GitHub.
*   **Funcionalidade:**
    *   Recebe uma requisição (`CommitRequest`) que inclui credenciais GitHub (token, nome de usuário, nome do repositório) e o conteúdo Markdown a ser commitado.
    *   Instancia um `GitHubCommitter` usando as credenciais fornecidas na requisição.
    *   Invoca o método `commitReadme` do `GitHubCommitter` para enviar o conteúdo e uma mensagem de commit padrão para o repositório especificado.
    *   Retorna uma mensagem de sucesso confirmando o commit ou um `500 Internal Server Error` se ocorrerem problemas durante a operação.

### src/main/java/gilgamesh/dto/CommitRequest.java
Aqui está o resumo solicitado:

---

### Resumo do Código: `src/main/java/gilgamesh/dto/CommitRequest.java`

**Responsabilidade da Classe `CommitRequest`:**

A classe `CommitRequest` atua como um **Objeto de Transferência de Dados (DTO - Data Transfer Object)**. Sua responsabilidade principal é encapsular e transportar os dados necessários para realizar uma requisição de "commit" (confirmação de alterações), provavelmente para um repositório Git/GitHub. Ela serve como um contêiner simples para os parâmetros de entrada de uma operação de commit, sem conter nenhuma lógica de negócio própria.

**Atributos encapsulados:**

*   `username`: O nome de usuário associado à requisição.
*   `repositoryName`: O nome do repositório onde o commit será realizado.
*   `githubToken`: Um token de autenticação/autorização para interagir com a API do GitHub.
*   `markdownContent`: O conteúdo em formato Markdown que será salvo ou atualizado no commit.

**Métodos Principais:**

Os métodos principais da classe são os pares de **`getters`** (métodos de leitura) e **`setters`** (métodos de escrita) para cada um dos seus atributos privados:

*   **`getUsername()` / `setUsername(String username)`:** Permitem ler e definir o nome de usuário da requisição.
*   **`getRepositoryName()` / `setRepositoryName(String repositoryName)`:** Permitem ler e definir o nome do repositório alvo.
*   **`getGithubToken()` / `setGithubToken(String githubToken)`:** Permitem ler e definir o token de autenticação do GitHub.
*   **`getMarkdownContent()` / `setMarkdownContent(String markdownContent)`:** Permitem ler e definir o conteúdo Markdown a ser commitado.

**Função dos Métodos:**

Todos esses métodos têm a função de **acessar e modificar** os valores dos atributos da classe. Eles fornecem a interface pública para a manipulação dos dados encapsulados, garantindo que o `CommitRequest` possa ser preenchido com as informações necessárias antes de ser utilizado em uma operação de commit. Eles não contêm lógica de validação ou processamento, servindo apenas para a manipulação direta dos dados.

### src/main/java/gilgamesh/dto/DocumentationRequest.java
Claro, aqui está o resumo do código Java `DocumentationRequest.java`:

---

### Resumo da Classe `DocumentationRequest`

**Arquivo:** `src/main/java/gilgamesh/dto/DocumentationRequest.java`

**Responsabilidades da Classe:**

A classe `DocumentationRequest` é um **Data Transfer Object (DTO)**, ou seja, um objeto simples cuja responsabilidade principal é encapsular e transportar dados. Sua finalidade é agrupar as informações essenciais necessárias para realizar uma requisição de documentação de código.

Ela atua como um contêiner para os seguintes dados:
*   **Nome de Usuário (`username`):** Identifica o usuário associado à requisição.
*   **Nome do Repositório (`repositoryName`):** Especifica qual repositório deve ser documentado.
*   **Token do GitHub (`githubToken`):** Um token de autenticação, provavelmente utilizado para acessar recursos privados ou protegidos em plataformas como o GitHub.

A classe `DocumentationRequest` não contém lógica de negócio; seu foco é apenas na estrutura e no transporte desses dados entre diferentes camadas ou serviços da aplicação.

**Métodos Principais:**

Os métodos da classe são um conjunto padrão de *getters* (para leitura) e *setters* (para escrita), que permitem o acesso e a modificação dos atributos privados da classe:

*   **`getUsername()`:**
    *   **Responsabilidade:** Retorna o nome de usuário associado à requisição.
*   **`setUsername(String username)`:**
    *   **Responsabilidade:** Define (altera) o nome de usuário da requisição.
*   **`getRepositoryName()`:**
    *   **Responsabilidade:** Retorna o nome do repositório a ser documentado.
*   **`setRepositoryName(String repositoryName)`:**
    *   **Responsabilidade:** Define (altera) o nome do repositório da requisição.
*   **`getGithubToken()`:**
    *   **Responsabilidade:** Retorna o token de autenticação do GitHub.
*   **`setGithubToken(String githubToken)`:**
    *   **Responsabilidade:** Define (altera) o token de autenticação do GitHub.

Em conjunto, esses métodos garantem que os dados encapsulados pela classe possam ser convenientemente acessados e manipulados por outras partes do sistema, mantendo o encapsulamento dos atributos internos.

### src/main/java/gilgamesh/service/CodeDocumenterService.java
Aqui está um resumo das responsabilidades da classe e seus métodos principais:

---

**Arquivo:** `src/main/java/gilgamesh/service/CodeDocumenterService.java`

### Resumo da Classe `CodeDocumenterService`

A classe `CodeDocumenterService` é responsável por **automatizar a geração de documentação e resumos para códigos Java** utilizando um modelo de linguagem grande (LLM - Large Language Model) da OpenAI, especificamente o GPT-3.5-turbo. Ela atua como uma ponte entre o conteúdo de arquivos Java e a capacidade de análise e geração de texto da inteligência artificial, construindo prompts adequados para cada tipo de solicitação de documentação.

### Métodos Principais

1.  **`CodeDocumenterService(String openAiApiKey)`**
    *   **Responsabilidade:** Construtor da classe. Sua principal responsabilidade é **inicializar a conexão com o modelo de chat da OpenAI** (`OpenAiChatModel`). Ele recebe uma chave de API da OpenAI (`openAiApiKey`) e configura o modelo a ser utilizado (no caso, "gpt-3.5-turbo") para que os métodos de documentação possam interagir com a IA.

2.  **`generateDocumentation(List<String> javaFileContents)`**
    *   **Responsabilidade:** Este método é projetado para **gerar uma documentação abrangente e consolidada para múltiplos arquivos Java**. Ele recebe uma lista de conteúdos de arquivos Java, agrupa-os em um único prompt detalhado e envia para o modelo de IA. O retorno esperado é um texto unificado gerado pela IA, explicando as classes e métodos contidos em todos os códigos fornecidos de forma detalhada.

3.  **`summarizeFilesIndividually(Map<String, String> fileContentsByPath)`**
    *   **Responsabilidade:** Ao contrário do método anterior, este método tem a função de **gerar um resumo individual para cada arquivo Java**. Ele recebe um mapa onde cada chave é o caminho de um arquivo e o valor é seu conteúdo. Para cada arquivo no mapa, ele cria um prompt específico solicitando um resumo focado nas responsabilidades da classe e seus métodos. O método retorna um novo mapa onde cada caminho de arquivo está associado ao seu resumo particular gerado pela IA.

### src/main/java/gilgamesh/service/DocumentationService.java
Aqui está um resumo das responsabilidades da classe `DocumentationService` e seus métodos principais:

---

### Resumo da Classe `gilgamesh.service.DocumentationService`

A classe `DocumentationService` é a **orquestradora central** para o processo de geração automática de documentação de código Java em um repositório GitHub. Sua responsabilidade principal é coordenar as diferentes etapas necessárias para transformar o código-fonte em um resumo formatado para um arquivo README.md. Ela faz isso integrando e delegando tarefas a outros serviços especializados, como a busca de código no GitHub e a sumarização de arquivos usando capacidades de Inteligência Artificial (OpenAI).

---

### Métodos Principais

1.  **`generateDocumentationForRepository(DocumentationRequest request)`**
    *   **Responsabilidade:** Este é o método **público e principal** da classe, servindo como o ponto de entrada para iniciar o fluxo de trabalho de documentação.
    *   **O que faz:** Ele gerencia o ciclo completo:
        1.  **Busca:** Instancia um `GitHubCodeFetcher` para buscar todos os arquivos Java de um repositório GitHub especificado na requisição.
        2.  **Sumarização:** Utiliza um `CodeDocumenterService` (que por sua vez usa a API do OpenAI) para gerar resumos individuais para cada arquivo de código Java encontrado.
        3.  **Formatação:** Chama o método auxiliar `buildReadmeSection` para formatar os resumos obtidos em uma string pronta para ser inserida em um README.md.
    *   **Retorno:** Uma string contendo a seção de documentação formatada.

2.  **`private String buildReadmeSection(Map<String, String> summaries)`**
    *   **Responsabilidade:** Este é um método **auxiliar interno** responsável exclusivamente pela formatação dos resumos de código em uma estrutura de texto legível para um arquivo README.md.
    *   **O que faz:** Recebe um mapa onde as chaves são os caminhos dos arquivos e os valores são seus respectivos resumos. Ele constrói uma `StringBuilder` adicionando um título para a seção de documentação, um texto introdutório, e então itera sobre cada resumo, formatando-o com um cabeçalho para o nome do arquivo e o conteúdo do resumo, tudo em sintaxe Markdown.
    *   **Retorno:** Uma string formatada em Markdown, pronta para ser anexada ao README.md do repositório.

### src/main/java/gilgamesh/service/GitHubCodeFetcher.java
Aqui está um resumo das responsabilidades da classe `GitHubCodeFetcher` e seus métodos principais:

---

## Resumo da Classe `GitHubCodeFetcher`

A classe `GitHubCodeFetcher` é um serviço especializado em interagir com a API do GitHub para extrair o conteúdo de arquivos Java (`.java`) de um repositório específico. Ela gerencia a autenticação com o GitHub e a navegação pela estrutura de diretórios do repositório para coletar os arquivos desejados.

### Responsabilidades Principais:

*   **Conexão e Autenticação:** Estabelecer e manter uma conexão autenticada com a API do GitHub.
*   **Acesso a Repositórios:** Obter referências a repositórios específicos no GitHub.
*   **Busca de Arquivos Java:** Percorrer a estrutura de diretórios de um repositório e identificar todos os arquivos com extensão `.java`.
*   **Extração de Conteúdo:** Recuperar o conteúdo textual dos arquivos Java encontrados.

### Métodos Principais:

1.  **`GitHubCodeFetcher(String token, String owner, String repoName)` (Construtor)**
    *   **Responsabilidade:** É responsável por inicializar a instância da classe, estabelecendo a conexão autenticada com a API do GitHub usando um token OAuth. Ele também configura o repositório-alvo para futuras operações e define timeouts específicos para as requisições de rede, garantindo que a comunicação não fique presa indefinidamente.

2.  **`public Map<String, String> fetchAllJavaFiles(String owner, String repoName)`**
    *   **Responsabilidade:** Este é o método principal para a extração de dados. Ele inicia o processo de busca recursiva, percorrendo todos os diretórios e subdiretórios do repositório especificado. Seu objetivo é coletar todos os arquivos com extensão `.java` e retornar um `Map` onde a **chave** é o caminho completo do arquivo dentro do repositório e o **valor** é o conteúdo textual desse arquivo.

3.  **`private void fetchJavaFilesRecursive(List<GHContent> contents, Map<String, String> javaFiles)`**
    *   **Responsabilidade:** Este é um método auxiliar (privado) que implementa a lógica central de travessia recursiva do conteúdo dos diretórios do GitHub. Ele itera sobre uma lista de itens (arquivos ou diretórios): se encontra um arquivo `.java`, adiciona seu caminho e conteúdo ao mapa de resultados; se encontra um diretório, chama a si mesmo recursivamente para explorar o conteúdo desse subdiretório. É fundamental para a capacidade da classe de navegar por estruturas de pastas aninhadas.

### src/main/java/gilgamesh/service/GitHubCommitter.java
Aqui está um resumo das responsabilidades da classe `GitHubCommitter` e seus métodos principais:

---

### Resumo da Classe `GitHubCommitter`

**Arquivo:** `src/main/java/gilgamesh/service/GitHubCommitter.java`

Esta classe é um serviço responsável por interagir com a API do GitHub para realizar operações de commit, especificamente focadas na criação e atualização do arquivo `README.md` em um repositório remoto. Ela abstrai a complexidade da comunicação com o GitHub, permitindo que outras partes da aplicação persistam conteúdo em um repositório.

#### Responsabilidades da Classe:

*   **Autenticação e Conexão:** Gerencia a autenticação com o GitHub usando um token OAuth e estabelece uma conexão com um repositório específico.
*   **Operações de Commit:** Realiza a criação ou atualização de arquivos no repositório, com foco principal no `README.md`.
*   **Persistência de Conteúdo:** Permite que conteúdo formatado (como o de um README) seja enviado e salvo no GitHub.

#### Métodos Principais:

1.  **`GitHubCommitter(String githubToken, String owner, String repoName)` (Construtor)**
    *   **Responsabilidade:** Inicializa a instância da classe `GitHubCommitter`.
    *   **Função:** Autentica-se na API do GitHub usando o `githubToken` fornecido e obtém uma referência ao repositório `repoName` pertencente ao `owner` especificado. Isso prepara a classe para realizar operações nesse repositório.

2.  **`commitReadme(String readmeContent, String commitMessage)`**
    *   **Responsabilidade:** Cria ou atualiza o arquivo `README.md` no repositório configurado.
    *   **Função:** Este é o método principal e ativo da classe para manipular o README. Ele recebe o conteúdo completo do `README.md` (já formatado por outro serviço) e uma mensagem para o commit. Se o `README.md` não existir, ele é criado; caso contrário, seu conteúdo é atualizado. O método lida com a lógica de interagir com a API do GitHub para persistir essas mudanças.

3.  **`createOrUpdateReadme(Map<String, String> fileSummaries)` (DEPRECADO)**
    *   **Responsabilidade:** **(Obsoleta)** Anteriormente, este método era responsável por criar e atualizar o `README.md` com uma seção de documentação automática gerada internamente a partir de um mapa de resumos.
    *   **Função:** Embora presente no código, está marcado como `@Deprecated`. Isso significa que sua lógica de **geração de conteúdo** foi movida para outro serviço (`DocumentationService`). Ele foi mantido apenas para referência, mas não deve ser utilizado em novos desenvolvimentos, pois a classe `GitHubCommitter` agora foca apenas na *persisência* do conteúdo, e não mais em sua *geração*.

---


