package gilgamesh.controller;

import gilgamesh.dto.CommitRequest;
import gilgamesh.dto.DocumentationRequest;
import gilgamesh.service.DocumentationService;
import gilgamesh.service.GitHubCommitter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/documentation")
public class DocumentationController {

    private final DocumentationService documentationService;

    public DocumentationController(DocumentationService documentationService) {
        this.documentationService = documentationService;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateDocumentation(@RequestBody DocumentationRequest request) {
        try {
            String readmeContent = documentationService.generateDocumentationForRepository(request);
            return ResponseEntity.ok(readmeContent);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro nos parâmetros da requisição: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ocorreu um erro interno ao gerar a documentação: " + e.getMessage());
        }
    }

    /**
     * NOVO ENDPOINT: Recebe as credenciais e o conteúdo de um README para commitar no repositório.
     */
    @PostMapping("/commit")
    public ResponseEntity<String> commitReadme(@RequestBody CommitRequest request) {
        try {
            // Instancia o GitHubCommitter com as credenciais da requisição
            GitHubCommitter committer = new GitHubCommitter(
                    request.getGithubToken(),
                    request.getUsername(),
                    request.getRepositoryName()
            );

            // Chama o novo metodo para commitar o conteúdo do README
            String commitMessage = "Docs: Atualiza README.md via API Gilgamesh";
            committer.commitReadme(request.getMarkdownContent(), commitMessage);

            return ResponseEntity.ok("README.md commitado com sucesso no repositório '" + request.getRepositoryName() + "'.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ocorreu um erro interno ao commitar o README: " + e.getMessage());
        }
    }
}