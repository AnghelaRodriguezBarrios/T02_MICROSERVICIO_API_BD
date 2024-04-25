package pe.edu.vallegrande.TranslatorTextT02.List;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

@Slf4j
@Service

public class TranslatorTextListado {
	
	// Languages - Devuelve el conjunto de idiomas admitidos actualmente por los métodos de traducción, 
	//transliteración y diccionario. Esta solicitud no requiere encabezados de autenticación y no necesita un recurso 
	//Translator para ver el conjunto de idiomas admitido.
	public static void main(String[] args) throws Exception {
        JSONObject languages = getAvailableLanguages();
        System.out.println(languages.toString(2));  // Imprime el resultado en formato JSON
    }

    public static JSONObject getAvailableLanguages() throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        // No es necesario especificar un MediaType para solicitudes GET que no llevan cuerpo.
        Request request = new Request.Builder()
                .url("https://api.cognitive.microsofttranslator.com/languages?api-version=3.0")
                .get()  // Usar .get() sin parámetro, ya que GET no lleva cuerpo.
                .addHeader("Ocp-Apim-Subscription-Key", "475d0ff690ec4c35a40f1dae92f00468")
                .addHeader("Content-Type", "application/json")
                .addHeader("Ocp-Apim-Subscription-Region", "eastus")
                .build();
        Response response = client.newCall(request).execute();

        // Parsear la respuesta JSON
        String responseBody = response.body().string();
        JSONObject jsonObject = new JSONObject(responseBody);
        log.info("Available languages: " + jsonObject);
        return jsonObject;
    }

}
