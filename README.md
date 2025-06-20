as

<!-- GILGAMESH-DOC-START -->
## 📄 Documentação Automática

Este trecho foi gerado pelo Gilgamesh com base nos arquivos do repositório.

### src/main/java/gilgamesh/Main.java
Resumo:
A classe Main é responsável por iniciar o processo de documentação de códigos Java. Ela utiliza instâncias das classes GitHubCodeFetcher, CodeDocumenterService e GitHubCommitter para buscar arquivos de código no GitHub, resumir esses arquivos utilizando uma API de inteligência artificial e atualizar um arquivo README com os resumos gerados. As responsabilidades dos métodos dentro da Main são principalmente iniciar e coordenar o processo de documentação de códigos.

### src/main/java/gilgamesh/service/CodeDocumenterService.java
Resumo da classe CodeDocumenterService:
- A classe é responsável por gerar documentação para códigos Java usando um modelo de chat da OpenAi.
- Ela possui um construtor que inicializa o serviço com a chave de API do OpenAi e o modelo de chat específico.
- O método generateDocumentation recebe uma lista de strings contendo o conteúdo completo de arquivos Java e gera documentação para eles, enviando um prompt para o modelo de chat e retornando a resposta.
- O método summarizeFilesIndividually recebe um mapa de strings contendo o conteúdo dos arquivos Java indexados pelo nome do arquivo e gera um resumo das responsabilidades da classe e seus métodos para cada arquivo individualmente. Ele também utiliza o modelo de chat da OpenAi para gerar os resumos.

### src/main/java/gilgamesh/service/GitHubCodeFetcher.java
Resumo da classe GitHubCodeFetcher:

Responsabilidades da classe:
- Conectar ao GitHub usando um token de autenticação fornecido.
- Buscar e recuperar arquivos Java de um repositório do GitHub.
- Armazenar os arquivos Java encontrados em um mapa de strings.

Métodos:
- GitHubCodeFetcher(String token, String owner, String repoName): construtor da classe que recebe um token de autenticação do GitHub, o nome do proprietário do repositório e o nome do repositório. Inicializa a conexão com o GitHub e obtém a referência para o repositório especificado.
- fetchAllJavaFiles(String owner, String repoName): método público que recupera todos os arquivos Java de um repositório especificado. Chama o método fetchJavaFilesRecursive para buscar os arquivos de forma recursiva e retorna um mapa de strings contendo os arquivos Java encontrados.
- fetchJavaFilesRecursive(List<GHContent> contents, Map<String, String> javaFiles): método privado que busca arquivos Java de forma recursiva, verificando se um conteúdo é um arquivo Java pelo seu nome terminado em ".java" e armazenando seu caminho e conteúdo no mapa de strings javaFiles. Se o conteúdo for um diretório, o método é chamado de forma recursiva para buscar os arquivos Java dentro deste diretório.

### src/main/java/gilgamesh/service/GitHubCommitter.java
Resumo da classe GitHubCommitter:

- Responsabilidades da classe:
A classe GitHubCommitter é responsável por interagir com a API do GitHub para realizar operações de commit em um repositório. Ela permite criar ou atualizar o conteúdo do arquivo README.md com uma seção de documentação automática.

- Métodos da classe:
1. Método construtor GitHubCommitter: Recebe um token de autenticação do GitHub, o nome do proprietário e do repositório, e inicializa a classe estabelecendo uma conexão com a API do GitHub e obtendo a referência ao repositório especificado.
2. Método createOrUpdateReadme: Cria ou atualiza o conteúdo do arquivo README.md com uma seção de documentação automática com base em um mapa de resumos de arquivos. Verifica se o arquivo README já existe, carrega seu conteúdo atual, atualiza ou anexa a seção de documentação e realiza o commit no repositório.
3. Método buildReadmeSection: Constrói a seção de documentação automática no README com base nos resumos de arquivos fornecidos no mapa.
4. Método updateAutoDocSection: Atualiza a seção de documentação automática no conteúdo atual do README, substituindo o conteúdo existente entre os marcadores de início e fim ou anexando a nova seção no final do arquivo, conforme necessário.

<!-- GILGAMESH-DOC-END -->
