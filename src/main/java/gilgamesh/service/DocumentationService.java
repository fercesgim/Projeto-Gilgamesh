package gilgamesh.service;

import gilgamesh.dto.DocumentationRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class DocumentationService {

    // Injeta a chave da API do arquivo application.properties
    @Value("${openai.api.key}")
    private String openAiApiKey;

    /**
     * Orquestra o processo de geração de documentação para um repositório.
     * @param request Contém o usuário, nome do repositório e token do GitHub.
     * @return A string de documentação formatada para o README.
     * @throws IOException Se houver erro na comunicação com o GitHub.
     */
    public String generateDocumentationForRepository(DocumentationRequest request) throws IOException {
        // 1. Busca os arquivos Java do repositório
        GitHubCodeFetcher fetcher = new GitHubCodeFetcher(request.getGithubToken(), request.getUsername(), request.getRepositoryName());
        Map<String, String> codeFiles = fetcher.fetchAllJavaFiles(request.getUsername(), request.getRepositoryName());

        // 2. Gera os resumos para cada arquivo usando o serviço de documentação
        CodeDocumenterService documenter = new CodeDocumenterService(openAiApiKey);
        Map<String, String> summaries = documenter.summarizeFilesIndividually(codeFiles);

        // 3. Constrói a seção do README e retorna como string
        return buildReadmeSection(summaries);
    }

    /**
     * Constrói a seção de documentação automática com base nos resumos.
     * A lógica foi inspirada no seu GitHubCommitter original.
     * @param summaries Mapa com os caminhos dos arquivos e seus respectivos resumos.
     * @return Uma string formatada para ser inserida no README.md.
     */
    private String buildReadmeSection(Map<String, String> summaries) {
        StringBuilder builder = new StringBuilder("\n");
        builder.append("## 📄 Documentação Automática\n\n");
        builder.append("Este trecho foi gerado pelo Gilgamesh com base nos arquivos do repositório.\n\n");

        for (Map.Entry<String, String> entry : summaries.entrySet()) {
            builder.append("### ").append(entry.getKey()).append("\n");
            builder.append("Resumo:\n"); // Adicionado para clareza
            builder.append(entry.getValue()).append("\n\n");
        }

        builder.append("\n");
        return builder.toString();
    }
}