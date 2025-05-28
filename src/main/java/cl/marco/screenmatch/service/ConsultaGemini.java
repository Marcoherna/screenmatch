package cl.marco.screenmatch.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

public class ConsultaGemini {

    private static final Client client = Client.builder()
            .apiKey(System.getenv("GEMINI_API_KEY"))
            .build();

    public static String obtenerTraduccionG(String texto) {
        try {
            GenerateContentResponse response = client.models.generateContent(
                "gemini-2.0-flash",
                "Traduce el siguiente texto al español: " + texto + " solo traduce el texto, no lo modifiques",
                null
            );

            return response.text();
        } catch (Exception e) {
            System.err.println("Error al obtener traducción: " + e.getMessage());
            return "Error en la traducción";
        }
//
//    public static class GenerateTextFromTextInput {
//        public static void main(String[] args) {
//            // Ejemplo de uso
//            String textoATraducir = "Hello, how are you?";
//            String traduccion = obtenerTraduccion(textoATraducir);
//            System.out.println("Traducción: " + traduccion);
//        }
    }
}


