package gilgamesh.service;

import org.kohsuke.github.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class GitHubCommitter {

    private final GHRepository repository;

    public GitHubCommitter(String githubToken, String owner, String repoName) throws IOException {
        GitHub github = new GitHubBuilder().withOAuthToken(githubToken).build();
        this.repository = github.getRepository(owner + "/" + repoName);
    }

    /**
     * @deprecated Este metodo foi mantido para referência, mas a lógica de geração de README
     * foi movida para o DocumentationService.
     */
    @Deprecated
    public void createOrUpdateReadme(Map<String, String> fileSummaries) throws IOException {
        String path = "README.md";
        String updatedReadmeContent;

        try {
            GHContent existingReadme = repository.getFileContent(path);
            String currentContent = new String(existingReadme.read().readAllBytes(), StandardCharsets.UTF_8);
            String newSection = buildReadmeSection(fileSummaries);
            updatedReadmeContent = updateAutoDocSection(currentContent, newSection);

            existingReadme.update(updatedReadmeContent, "Atualizando seção de documentação automática no README");
            System.out.println("README.md atualizado com nova seção de documentação.");
        } catch (GHFileNotFoundException e) {
            updatedReadmeContent = "# README\n\n" + buildReadmeSection(fileSummaries);
            repository.createContent()
                    .path(path)
                    .content(updatedReadmeContent)
                    .message("Criando README com documentação automática")
                    .commit();
            System.out.println("README.md criado com sucesso.");
        }
    }

    /**
     * NOVO METODO: Cria ou atualiza o arquivo README.md com um conteúdo de string fornecido.
     *
     * @param readmeContent O conteúdo completo do arquivo README.md.
     * @param commitMessage A mensagem a ser usada para o commit.
     * @throws IOException Se ocorrer um erro ao interagir com a API do GitHub.
     */
    public void commitReadme(String readmeContent, String commitMessage) throws IOException {
        String path = "README.md";
        try {
            // Tenta obter o arquivo README.md existente
            GHContent existingReadme = repository.getFileContent(path);
            // Se existir, atualiza o conteúdo
            existingReadme.update(readmeContent, commitMessage);
            System.out.println("README.md atualizado com sucesso no repositório.");
        } catch (GHFileNotFoundException e) {
            // Se não existir, cria um novo arquivo README.md
            repository.createContent()
                    .path(path)
                    .content(readmeContent)
                    .message(commitMessage)
                    .commit();
            System.out.println("README.md criado com sucesso no repositório.");
        }
    }


    private String buildReadmeSection(Map<String, String> summaries) {
        StringBuilder builder = new StringBuilder("\n");
        builder.append("## 📄 Documentação Automática\n\n");
        builder.append("Este trecho foi gerado pelo Gilgamesh com base nos arquivos do repositório.\n\n");

        for (Map.Entry<String, String> entry : summaries.entrySet()) {
            builder.append("### ").append(entry.getKey()).append("\n");
            builder.append(entry.getValue()).append("\n\n");
        }

        builder.append("\n");
        return builder.toString();
    }

    private String updateAutoDocSection(String currentContent, String newSection) {
        String startMarker = "";
        String endMarker = "";

        if (currentContent.contains(endMarker)) {
            return currentContent.replaceAll(
                    startMarker + ".*?" + endMarker,
                    newSection.replace("$", "\\$")
            );
        } else {
            return currentContent.trim() + "\n\n" + newSection;
        }
    }
}