# README

<!-- GILGAMESH-DOC-START -->
## 📄 Documentação Automática

Este trecho foi gerado pelo Gilgamesh com base nos arquivos do repositório.

### src/main/java/gilgamesh/Main.java
Resumo da classe Main:

A classe Main é responsável por inicializar o programa e executar as seguintes ações:

- Instanciar um objeto GitHubCodeFetcher para buscar arquivos Java de um repositório no GitHub.
- Documentar os códigos Java usando um serviço de documentação chamado CodeDocumenterService.
- Instanciar um objeto GitHubCommitter para criar ou atualizar um arquivo README.md no repositório do GitHub com um resumo dos códigos documentados.

### src/main/java/gilgamesh/service/CodeDocumenterService.java
Resumo da classe CodeDocumenterService:

Responsabilidades da classe:
- Gerar documentação para uma lista de arquivos Java.
- Resumir individualmente os arquivos Java, fornecendo as responsabilidades das classes e métodos.

Métodos:
1. Constructor CodeDocumenterService(String openAiApiKey): Construtor da classe que inicializa o chatModel com as configurações do OpenAI.

2. generateDocumentation(List<String> javaFileContents): Método que gera a documentação para uma lista de arquivos Java. Constrói um prompt com o conteúdo dos arquivos, envia para a LLM (Language Model) e retorna a resposta.

3. summarizeFilesIndividually(Map<String, String> fileContentsByPath): Método que resumidamente analisa e documenta os códigos Java individualmente. Para cada arquivo, constrói um prompt específico, envia para a LLM e armazena o resumo obtido em um mapa de resumos.

### src/main/java/gilgamesh/service/GitHubCodeFetcher.java
Resumo:
A classe GitHubCodeFetcher é responsável por obter e armazenar arquivos Java de um repositório no GitHub. Ela possui um construtor que recebe um token de autenticação do GitHub, o nome do proprietário e o nome do repositório. A classe utiliza a API Java do GitHub para se autenticar e obter o repositório desejado.

Métodos:
1. Construtor GitHubCodeFetcher: Responsável por autenticar o usuário no GitHub e obter o repositório especificado.
   
2. fetchAllJavaFiles(String owner, String repoName): Retorna um mapa contendo o caminho e o conteúdo de todos os arquivos Java presentes no repositório informado.

3. fetchJavaFilesRecursive(List<GHContent> contents, Map<String, String> javaFiles): Método privado que, de forma recursiva, percorre os conteúdos do repositório em busca de arquivos Java. Os arquivos encontrados são adicionados ao mapa javaFiles.

### src/main/java/gilgamesh/service/GitHubCommitter.java
Resumo:
A classe GitHubCommitter é responsável por gerenciar a comunicação com o GitHub e atualizar o README.md de um repositório. Ela possui um construtor que recebe um token de autenticação, o dono do repositório e o nome do repositório, e inicializa a conexão com o GitHub. O método createOrUpdateReadme é responsável por criar e atualizar o conteúdo do README.md com uma seção de documentação automática, com base em um mapa de resumos de arquivos. Caso o README já exista, ele preserva o conteúdo atual e atualiza a seção de documentação, caso contrário, ele cria o README do zero. Os métodos buildReadmeSection e updateAutoDocSection auxiliam na construção da nova seção de documentação e na atualização do conteúdo do README, respeitando os marcadores de início e fim da seção de documentação automática. No final, o método imprime mensagens informando sobre o sucesso na criação ou atualização do README.md.

<!-- GILGAMESH-DOC-END -->
