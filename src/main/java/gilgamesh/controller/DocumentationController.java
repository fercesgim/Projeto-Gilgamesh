package gilgamesh.controller;

import gilgamesh.dto.CommitRequest;
import gilgamesh.dto.DocumentationRequest;
import gilgamesh.service.DocumentationService;
import gilgamesh.service.GitHubCommitter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/documentation")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Documentação", description = "Endpoints para geração e commit de documentação de repositórios.")
public class DocumentationController {

    private final DocumentationService documentationService;

    public DocumentationController(DocumentationService documentationService) {
        this.documentationService = documentationService;
    }

    @Operation(
            summary = "Gera a documentação de um repositório",
            description = """
                          Este endpoint aciona o fluxo principal da aplicação para gerar documentação.
                          A lógica interna executa os seguintes passos:
                          1.  Utiliza o serviço **GitHubCodeFetcher** para buscar todos os arquivos `.java` do repositório especificado.
                          2.  Para cada arquivo, o serviço **CodeDocumenterService** envia o código para a API do Google Gemini, solicitando um resumo em português.
                          3.  O serviço **DocumentationService** compila todos os resumos recebidos em um único texto formatado em Markdown.
                          4.  O conteúdo final em Markdown é retornado no corpo da resposta.
                          """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documentação gerada com sucesso",
                    content = @Content(mediaType = "text/plain", schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "500", description = "Erro interno ao gerar a documentação",
                    content = @Content)
    })
    @PostMapping("/generate")
    public ResponseEntity<String> generateDocumentation(@RequestBody DocumentationRequest request) {
        try {
            String content = documentationService.generateDocumentationForRepository(
                    request.getUsername(),
                    request.getRepositoryName(),
                    request.getGithubToken()
            );
            return ResponseEntity.ok(content);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao gerar documentação: " + e.getMessage());
        }
    }

    @Operation(
            summary = "Salva a documentação em um arquivo README.md no repositório",
            description = """
                          Este endpoint realiza o commit do conteúdo Markdown (geralmente obtido através do endpoint /generate) em um arquivo README.md no repositório GitHub.
                          A lógica interna executa os seguintes passos:
                          1.  Utiliza o serviço **GitHubCommitter** e a API do GitHub para a autenticação.
                          2.  Verifica se já existe um arquivo `README.md`.
                          3.  Se o arquivo existir, ele é atualizado. Se não, um novo arquivo `README.md` é criado.
                          4.  A mensagem de commit utilizada é "docs: README.md atualizado via API Gilgamesh".
                          """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "README commitado com sucesso", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar o commit do README", content = @Content)
    })
    @PostMapping("/commit")
    public ResponseEntity<String> commitReadme(@RequestBody CommitRequest request) {
        try {
            GitHubCommitter committer = new GitHubCommitter(
                    request.getGithubToken(), request.getUsername(), request.getRepositoryName()
            );
            committer.commitReadme(request.getMarkdownContent(), "docs: README.md atualizado via API Gilgamesh");
            return ResponseEntity.ok("README commitado com sucesso!");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Erro ao commitar README: " + e.getMessage());
        }
    }
}