package gilgamesh.service;

import dev.langchain4j.model.chat.ChatLanguageModel;
// Importação do modelo do Google Gemini
import dev.langchain4j.model.google.GoogleAiChatModel;

import java.util.LinkedHashMap;
import java.util.Map;

public class CodeDocumenterService {

    private final ChatLanguageModel model;

    // O construtor agora recebe a chave do Gemini
    public CodeDocumenterService(String geminiApiKey) {
        this.model = GoogleAiChatModel.builder()
                .apiKey(geminiApiKey)
                .modelName("gemini-pro") // ou outro modelo como "gemini-1.5-flash"
                .build();
    }

    public Map<String, String> summarizeFilesIndividually(Map<String, String> fileContentsByPath) {
        Map<String, String> summaries = new LinkedHashMap<>();

        for (Map.Entry<String, String> entry : fileContentsByPath.entrySet()) {
            String fileName = entry.getKey();
            String code = entry.getValue();

            String prompt = String.format(
                    "Você é um assistente que documenta códigos Java.\n"
                            + "Analise o seguinte código e gere um resumo com as responsabilidades da classe e seus métodos principais em português claro e objetivo:\n\n"
                            + "Arquivo: %s\n\n```java\n%s\n```", fileName, code);

            // A chamada para o modelo permanece a mesma
            String summary = model.generate(prompt);
            summaries.put(fileName, summary.trim());
        }

        return summaries;
    }
}