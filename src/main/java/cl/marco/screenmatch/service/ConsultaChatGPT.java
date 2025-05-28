package cl.marco.screenmatch.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsultaChatGPT {
    public static String obtenerTraduccion(String texto) {
        OpenAiService service = new OpenAiService(System.getenv("OPENAI_API_KEY"));

        CompletionRequest requisicion = CompletionRequest.builder()
                .model("gpt-4.1")
                .prompt("traduce el siguiente texto al español: " + texto + " solo traduce el texto")
                .maxTokens(1000)
                .temperature(0.7)
                .build();
        var respuesta = service.createCompletion(requisicion);
        return respuesta.getChoices().get(0).getText();
    }
}
