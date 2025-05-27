package gilgamesh;

import gilgamesh.service.CodeDocumenterService;
import gilgamesh.service.GitHubCodeFetcher;
import gilgamesh.service.GitHubCommitter;

import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        String openAiApiKey = "";
        String githubToken = "";

        GitHubCodeFetcher fetcher = new GitHubCodeFetcher(githubToken, "Viniquest", "Gilgamesh");
        Map<String, String> codeFiles = fetcher.fetchAllJavaFiles("Viniquest", "Gilgamesh");

        CodeDocumenterService documenter = new CodeDocumenterService(openAiApiKey);
        Map<String, String> summaries = documenter.summarizeFilesIndividually(codeFiles);

        GitHubCommitter committer = new GitHubCommitter(githubToken, "Viniquest", "Gilgamesh");
        committer.createOrUpdateReadme(summaries);
    }
}
