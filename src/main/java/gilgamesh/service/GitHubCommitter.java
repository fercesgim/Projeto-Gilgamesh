package gilgamesh.service;

import org.kohsuke.github.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class GitHubCommitter {

    private final GitHub github;
    private final GHRepository repository;

    public GitHubCommitter(String githubToken, String owner, String repoName) throws IOException {
        this.github = new GitHubBuilder().withOAuthToken(githubToken).build();
        this.repository = github.getRepository(owner + "/" + repoName);
    }

    public void createOrUpdateReadme(Map<String, String> fileSummaries) throws IOException {
        String path = "README.md";
        String updatedReadmeContent;

        try {
            // Se o README já existe, carrega e preserva conteúdo atual
            GHContent existingReadme = repository.getFileContent(path);
            String currentContent = new String(existingReadme.read().readAllBytes(), StandardCharsets.UTF_8);

            // Atualiza ou anexa a seção de documentação automática
            String newSection = buildReadmeSection(fileSummaries);
            updatedReadmeContent = updateAutoDocSection(currentContent, newSection);

            existingReadme.update(updatedReadmeContent, "Atualizando seção de documentação automática no README");
            System.out.println("README.md atualizado com nova seção de documentação.");
        } catch (GHFileNotFoundException e) {
            // Cria README do zero
            updatedReadmeContent = "# README\n\n" + buildReadmeSection(fileSummaries);
            repository.createContent()
                    .path(path)
                    .content(updatedReadmeContent)
                    .message("Criando README com documentação automática")
                    .commit();
            System.out.println("README.md criado com sucesso.");
        }
    }

    private String buildReadmeSection(Map<String, String> summaries) {
        StringBuilder builder = new StringBuilder("<!-- GILGAMESH-DOC-START -->\n");
        builder.append("## 📄 Documentação Automática\n\n");
        builder.append("Este trecho foi gerado pelo Gilgamesh com base nos arquivos do repositório.\n\n");

        for (Map.Entry<String, String> entry : summaries.entrySet()) {
            builder.append("### ").append(entry.getKey()).append("\n");
            builder.append(entry.getValue()).append("\n\n");
        }

        builder.append("<!-- GILGAMESH-DOC-END -->\n");
        return builder.toString();
    }

    private String updateAutoDocSection(String currentContent, String newSection) {
        String startMarker = "<!-- GILGAMESH-DOC-START -->";
        String endMarker = "<!-- GILGAMESH-DOC-END -->";

        if (currentContent.contains(startMarker) && currentContent.contains(endMarker)) {
            // Substitui o conteúdo entre os marcadores
            return currentContent.replaceAll(
                    startMarker + ".*?" + endMarker,
                    newSection.replace("$", "\\$")
            );
        } else {
            // Anexa nova seção no final do README
            return currentContent.trim() + "\n\n" + newSection;
        }
    }
}
