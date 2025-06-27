package gilgamesh.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Map;

@Service
public class DocumentationService {

    // ----- LINHA MODIFICADA ABAIXO -----
    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public String generateDocumentationForRepository(String username, String repoName, String githubToken) throws IOException {
        GitHubCodeFetcher fetcher = new GitHubCodeFetcher(githubToken, username, repoName);
        Map<String, String> codeFiles = fetcher.fetchAllJavaFiles(username, repoName);

        // ----- LINHA MODIFICADA ABAIXO -----
        CodeDocumenterService documenter = new CodeDocumenterService(geminiApiKey);

        Map<String, String> summaries = documenter.summarizeFilesIndividually(codeFiles);

        System.out.println(">>>>>> [DEBUG] Arquivos .java encontrados: " + codeFiles.size());
        System.out.println(">>>>>> [DEBUG] Resumos gerados: " + summaries.size());

        return buildReadmeSection(summaries);
    }

    private String buildReadmeSection(Map<String, String> summaries) {
        // ... (código existente sem alterações)
        StringBuilder builder = new StringBuilder("\n");
        builder.append("## 📄 Documentação Automática\n\n");
        for (Map.Entry<String, String> entry : summaries.entrySet()) {
            builder.append("### ").append(entry.getKey()).append("\n");
            builder.append(entry.getValue()).append("\n\n");
        }
        builder.append("\n");
        return builder.toString();
    }
}