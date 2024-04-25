package pe.edu.vallegrande.TranslatorTextT02.Service;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Slf4j
@Service
public class TranslatorTextService01 {

	// Translate - Traduce el texto de idioma de origen especificado en el texto del idioma de destino.
	public static void main(String[] args) throws Exception {
        JSONObject translationResult = translateText();
        System.out.println(translationResult.toString(2));  // Imprime el resultado en formato JSON
    }

    public static JSONObject translateText() throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        @SuppressWarnings("deprecation")
		RequestBody body = RequestBody.create(mediaType, 
                "[\n    {\n        \"Text\": \"Hello Teacher Juan Condori, how are you?\"\n    }\n]");
        Request request = new Request.Builder()
                .url("https://api.cognitive.microsofttranslator.com/translate?api-version=3.0&from=en&to=zh-Hans&to=de")
                .method("POST", body)
                .addHeader("Ocp-Apim-Subscription-Key", "475d0ff690ec4c35a40f1dae92f00468")
                .addHeader("Content-Type", "application/json")
                .addHeader("Ocp-Apim-Subscription-Region", "eastus")
                .build();
        Response response = client.newCall(request).execute();

        // Parsear la respuesta JSON
        String responseBody = response.body().string();
        JSONArray jsonArray = new JSONArray(responseBody);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        log.info("Translation result: " + jsonObject);
        return jsonObject;
    }

}