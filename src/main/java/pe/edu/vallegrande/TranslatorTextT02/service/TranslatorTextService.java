package pe.edu.vallegrande.TranslatorTextT02.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import pe.edu.vallegrande.TranslatorTextT02.model.TranslatorText;
import pe.edu.vallegrande.TranslatorTextT02.repository.TranslatorTextRepository;
import reactor.core.publisher.Mono;

@Service
public class TranslatorTextService {

    private static final Logger log = LoggerFactory.getLogger(TranslatorTextService.class);
    private final TranslatorTextRepository translatorTextRepository;
    private final WebClient webClient;

    @Value("${spring.translator.api.key}")
    private String apiKey;

    public TranslatorTextService(TranslatorTextRepository translatorTextRepository, WebClient.Builder webClientBuilder) {
        this.translatorTextRepository = translatorTextRepository;
        this.webClient = webClientBuilder
            .baseUrl("https://api.cognitive.microsofttranslator.com")
            .defaultHeader("Ocp-Apim-Subscription-Key", apiKey)
            .build();
    }

    public Mono<TranslatorText> saveTranslation(TranslatorText translation) {
        return translatorTextRepository.save(translation);
    }

    public Mono<TranslatorText> getTranslationById(Long id) {
        return translatorTextRepository.findById(id);
    }

    public Mono<TranslatorText> translateText(String text, String from, String to) {
    	return webClient.post()
    		    .uri(uriBuilder -> uriBuilder.path("/translate")
    		        .queryParam("api-version", "3.0")
    		        .queryParam("from", from)
    		        .queryParam("to", to)
    		        .build())
    		    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
    		    .bodyValue("[{\"Text\":\"" + text + "\"}]")
    		    .retrieve()
            .onStatus(status -> status.isError(), response -> 
                response.bodyToMono(String.class)
                    .flatMap(errorBody -> {
                        log.error("Error from server: {}", errorBody);
                        return Mono.error(new RuntimeException("Server responded with error: " + errorBody));
                    }))
            .bodyToMono(String.class)
            .flatMap(translatedText -> {
                TranslatorText translation = new TranslatorText();
                translation.setSourceText(text);
                translation.setTranslatedText(translatedText);
                translation.setSourceLanguage(from);
                translation.setTargetLanguage(to);
                return saveTranslation(translation);
            })
            .onErrorMap(WebClientResponseException.class, ex -> {
                log.error("API call failed with status: {} and body: {}", ex.getStatusCode(), ex.getResponseBodyAsString());
                return new RuntimeException("API call failed with status: " + ex.getStatusCode() + " and body: " + ex.getResponseBodyAsString(), ex);
            });
    }
}
