
## 📄 Documentação Automática

Este trecho foi gerado pelo Gilgamesh com base nos arquivos do repositório.

### src/main/java/gilgamesh/GilgameshApplication.java
Resumo:
Resumo:
A classe GilgameshApplication é a classe principal da aplicação. Ela é responsável por inicializar e configurar a aplicação utilizando o framework Spring Boot. O método main é responsável por iniciar a aplicação Spring ao chamar o método SpringApplication.run(). A anotação @SpringBootApplication indica que esta classe é uma classe de configuração do Spring Boot e que a aplicação deve ser iniciada a partir dela.

### src/main/java/gilgamesh/controller/DocumentationController.java
Resumo:
Resumo da classe DocumentationController:

- A classe é responsável por receber requisições relacionadas à geração e commit de documentação em um repositório.
- Ela possui dois métodos POST: generateDocumentation e commitReadme, que aceitam objetos DocumentationRequest e CommitRequest, respectivamente.
- O método generateDocumentation chama o serviço DocumentationService para gerar a documentação com base nos parâmetros recebidos na requisição.
- O método commitReadme instancia o GitHubCommitter com as credenciais fornecidas na requisição e realiza o commit do conteúdo do README no repositório.
- Ambos os métodos tratam exceções e retornam ResponseEntity com informações sobre o sucesso ou falha da operação.

### src/main/java/gilgamesh/dto/CommitRequest.java
Resumo:
Resumo:
A classe CommitRequest é responsável por representar um pedido de commit no GitHub, contendo informações como o nome de usuário, nome do repositório, token do GitHub e conteúdo em formato markdown. Ela possui métodos getters e setters para acessar e modificar essas informações.

### src/main/java/gilgamesh/dto/DocumentationRequest.java
Resumo:
Resumo:

A classe DocumentationRequest é responsável por representar uma solicitação de documentação, contendo as informações do usuário, nome do repositório e token do GitHub. 

Métodos da Classe:
- getUsername(): retorna o nome de usuário associado à solicitação de documentação.
- setUsername(String username): define o nome de usuário associado à solicitação de documentação.
- getRepositoryName(): retorna o nome do repositório associado à solicitação de documentação.
- setRepositoryName(String repositoryName): define o nome do repositório associado à solicitação de documentação.
- getGithubToken(): retorna o token do GitHub associado à solicitação de documentação.
- setGithubToken(String githubToken): define o token do GitHub associado à solicitação de documentação.

### src/main/java/gilgamesh/service/CodeDocumenterService.java
Resumo:
Resumo da Classe CodeDocumenterService:

- A classe CodeDocumenterService é responsável por gerar documentação para códigos Java utilizando um modelo de Chat.

Método generateDocumentation:
- Responsabilidade: Gera a documentação para uma lista de arquivos Java (conteúdo do código).
- Parâmetro: Lista de strings com o código completo de cada arquivo Java.
- Retorno: Documentação gerada pela LLM (Modelo de Linguagem).
- Funcionamento: Monta um prompt com o conteúdo dos arquivos Java, envia para o modelo de Chat e retorna a resposta.

Método summarizeFilesIndividually:
- Responsabilidade: Gera um resumo das responsabilidades da classe e seus métodos para cada arquivo Java individualmente.
- Parâmetro: Mapa contendo o conteúdo dos arquivos Java mapeados pelo nome do arquivo.
- Retorno: Mapa contendo o nome do arquivo e o resumo gerado para cada um.
- Funcionamento: Cria um prompt para cada arquivo Java, envia para o modelo de Chat e armazena o resumo gerado no mapa de resumos.

### src/main/java/gilgamesh/service/DocumentationService.java
Resumo:
Resumo da Classe DocumentationService:

Responsabilidades:
- Orquestrar o processo de geração de documentação para um repositório.
- Injetar a chave da API do arquivo application.properties.

Métodos:
- generateDocumentationForRepository(DocumentationRequest request): Orquestra o processo de geração de documentação para um repositório, buscando os arquivos Java do repositório, gerando resumos para cada arquivo e construindo a seção do README.
- buildReadmeSection(Map<String, String> summaries): Constrói a seção de documentação automática com base nos resumos fornecidos.

### src/main/java/gilgamesh/service/GitHubCodeFetcher.java
Resumo:
Resumo da classe GitHubCodeFetcher:

A classe GitHubCodeFetcher é responsável por buscar e recuperar arquivos Java de um repositório do GitHub.

Métodos:
- Construtor GitHubCodeFetcher: recebe um token de autenticação do GitHub, o dono do repositório e o nome do repositório como parâmetros. Inicializa a instância do GitHub com o token fornecido e obtém o repositório especificado.
- fetchAllJavaFiles: recebe o dono do repositório e o nome do repositório como parâmetros e retorna um mapa contendo o caminho e o conteúdo de todos os arquivos Java do repositório. Chama o método fetchJavaFilesRecursive para percorrer de forma recursiva os diretórios do repositório.
- fetchJavaFilesRecursive: método privado que recebe uma lista de conteúdos do repositório e um mapa para armazenar os arquivos Java encontrados. Itera sobre os conteúdos verificando se são arquivos Java e os armazena no mapa, ou se são diretórios e chama recursivamente o método para buscar arquivos dentro deles.

### src/main/java/gilgamesh/service/GitHubCommitter.java
Resumo:
Resumo da classe GitHubCommitter:
- A classe GitHubCommitter é responsável por interagir com o serviço do GitHub para criar ou atualizar o arquivo README.md de um repositório.
- Ela possui um construtor que inicializa a conexão com o GitHub utilizando um token de acesso e o nome do proprietário e do repositório.
- O método createOrUpdateReadme cria ou atualiza o arquivo README.md com base em uma lista de sumários fornecidos. O conteúdo do arquivo é gerado automaticamente com base nos sumários.
- O método commitReadme permite criar ou atualizar o arquivo README.md com um conteúdo fornecido e uma mensagem de commit.
- Os métodos buildReadmeSection e updateAutoDocSection são métodos auxiliares para construir e atualizar as seções de documentação no arquivo README.md. O updateAutoDocSection substitui a seção de documentação existente com a nova seção.
