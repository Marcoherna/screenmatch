package cl.marco.screenmatch.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsultaChatGPT {
    public static String obtenerTraduccion(String texto) {
        OpenAiService service = new OpenAiService("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");

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
