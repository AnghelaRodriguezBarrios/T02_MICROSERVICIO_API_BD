package pe.edu.vallegrande.TranslatorTextT02.Service;

import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

@Slf4j
@Service

public class TranslatorTextService02 {
	// BreakSentence - 	Devuelve una matriz de enteros que representa la longitud de las oraciones en un texto de origen.
	public static void main(String[] args) throws Exception {
        JSONArray breakResult = breakSentence();
        System.out.println(breakResult.toString(2));  // Imprime el resultado en formato JSON
    }

    public static JSONArray breakSentence() throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        @SuppressWarnings("deprecation")
		RequestBody body = RequestBody.create(mediaType, 
                "[\r\n    { \"Text\": \"Hola soy Anghela, estudio la carrera de Analisis de Sistemas en Valle Grande\" }\r\n]");
        Request request = new Request.Builder()
                .url("https://api.cognitive.microsofttranslator.com/breaksentence?api-version=3.0")
                .method("POST", body)
                .addHeader("Ocp-Apim-Subscription-Key", "475d0ff690ec4c35a40f1dae92f00468")
                .addHeader("Content-Type", "application/json")
                .addHeader("Ocp-Apim-Subscription-Region", "eastus")
                .build();
        Response response = client.newCall(request).execute();

        // Parsear la respuesta JSON
        String responseBody = response.body().string();
        JSONArray jsonArray = new JSONArray(responseBody);
        log.info("Sentence break result: " + jsonArray);
        return jsonArray;
    }

}