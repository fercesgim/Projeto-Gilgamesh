package gilgamesh.service;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CodeDocumenterService {

    private final ChatModel chatModel;

    public CodeDocumenterService(String openAiApiKey) {
        this.chatModel = OpenAiChatModel.builder()
                .apiKey(openAiApiKey)
                .modelName("gpt-3.5-turbo")
                .build();
    }

    /**
     * Gera a documentação para uma lista de arquivos Java (conteúdo do código).
     * @param javaFileContents Lista de strings, cada uma com o código completo de um arquivo Java.
     * @return Documentação gerada pela LLM.
     */
    public String generateDocumentation(List<String> javaFileContents) {
        // Montar prompt com o conteúdo dos arquivos
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("Você é um assistente que gera documentação para códigos Java.\n");
        promptBuilder.append("Para os códigos abaixo, gere uma documentação clara e objetiva explicando classes e métodos.\n\n");

        for (String fileContent : javaFileContents) {
            promptBuilder.append(fileContent).append("\n\n");
        }

        promptBuilder.append("Por favor, faça a documentação de forma detalhada.");

        // Enviar prompt para a LLM e obter resposta
        String response = chatModel.chat(promptBuilder.toString());

        return response;
    }

    public Map<String, String> summarizeFilesIndividually(Map<String, String> fileContentsByPath) {
        Map<String, String> summaries = new LinkedHashMap<>();

        for (Map.Entry<String, String> entry : fileContentsByPath.entrySet()) {
            String fileName = entry.getKey();
            String code = entry.getValue();

            String prompt = String.format(
                    "Você é um assistente que documenta códigos Java.\n"
                            + "Analise o seguinte código e gere um resumo com as responsabilidades da classe e seus métodos:\n\n"
                            + "Arquivo: %s\n\n%s", fileName, code);

            String summary = chatModel.chat(prompt);
            summaries.put(fileName, summary.trim());
        }

        return summaries;
    }

}
