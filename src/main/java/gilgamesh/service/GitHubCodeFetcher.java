package gilgamesh.service;

import org.kohsuke.github.*;

import java.io.IOException;
import java.util.*;

public class GitHubCodeFetcher {

    private final GitHub github;
    private final GHRepository repository;

    public GitHubCodeFetcher(String token, String owner, String repoName) throws IOException {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token do GitHub está vazio ou não definido.");
        }

        this.github = new GitHubBuilder()
                .withOAuthToken(token)
                .withConnector(new HttpConnector() {
                    @Override
                    public java.net.HttpURLConnection connect(java.net.URL url) throws IOException {
                        java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
                        connection.setConnectTimeout(5000); // 5s para conectar
                        connection.setReadTimeout(10000);   // 10s para ler
                        return connection;
                    }
                })
                .build();

        this.repository = github.getRepository(owner + "/" + repoName);
    }

    public Map<String, String> fetchAllJavaFiles(String owner, String repoName) throws IOException {
        GHRepository repository = github.getRepository(owner + "/" + repoName);
        Map<String, String> javaFiles = new LinkedHashMap<>();
        fetchJavaFilesRecursive(repository.getDirectoryContent(""), javaFiles);
        return javaFiles;
    }

    private void fetchJavaFilesRecursive(List<GHContent> contents, Map<String, String> javaFiles) throws IOException {
        for (GHContent content : contents) {
            if (content.isFile() && content.getName().endsWith(".java")) {
                javaFiles.put(content.getPath(), content.getContent());
            } else if (content.isDirectory()) {
                List<GHContent> subDirContents = content.listDirectoryContent().toList();
                fetchJavaFilesRecursive(subDirContents, javaFiles);
            }
        }
    }
}
