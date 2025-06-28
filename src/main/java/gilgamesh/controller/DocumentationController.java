package gilgamesh.controller;

import gilgamesh.dto.CommitRequest;
import gilgamesh.dto.DocumentationRequest;
import gilgamesh.service.DocumentationService;
import gilgamesh.service.GitHubCommitter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/documentation")
@CrossOrigin(origins = "http://localhost:3000") // Permite a comunicação com o frontend
public class DocumentationController {

    private final DocumentationService documentationService;

    public DocumentationController(DocumentationService documentationService) {
        this.documentationService = documentationService;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateDocumentation(@RequestBody DocumentationRequest request) {
        try {
            // Extraímos os campos do objeto 'request' e os passamos como Strings separadas,
            // que é o que o método no serviço agora espera.
            String content = documentationService.generateDocumentationForRepository(
                    request.getUsername(),
                    request.getRepositoryName(),
                    request.getGithubToken()
            );
            return ResponseEntity.ok(content);
        } catch (Exception e) {
            // Adicionado para dar mais detalhes do erro no frontend
            return ResponseEntity.internalServerError().body("Erro ao gerar documentação: " + e.getMessage());
        }
    }

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