package pe.edu.vallegrande.TranslatorTextT02.Service;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service

public class TranslatorTextService03 {
	// Detect - Identifica el idioma de origen.
	private static final String API_URL = "https://api.cognitive.microsofttranslator.com/detect?api-version=3.0";
    private static final String SUBSCRIPTION_KEY = "475d0ff690ec4c35a40f1dae92f00468";
    private static final String SUBSCRIPTION_REGION = "eastus";
    private static final OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public static JSONArray detectLanguage(String text) throws IOException {
        @SuppressWarnings("deprecation")
		RequestBody body = RequestBody.create(JSON, createJsonPayload(text));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .addHeader("Ocp-Apim-Subscription-Key", SUBSCRIPTION_KEY)
                .addHeader("Content-Type", "application/json")
                .addHeader("Ocp-Apim-Subscription-Region", SUBSCRIPTION_REGION)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            String responseBody = response.body().string();
            return new JSONArray(responseBody);
        }
    }

    private static String createJsonPayload(String text) {
        return "[{\"Text\": \"" + text + "\"}]";
    }

    public static void main(String[] args) {
        try {
            JSONArray result = detectLanguage("Sou estudante de Analise de Sistemas e estou no quinto semestre");
            System.out.println(result.toString(2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
