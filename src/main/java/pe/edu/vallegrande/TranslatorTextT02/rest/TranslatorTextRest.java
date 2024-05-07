package pe.edu.vallegrande.TranslatorTextT02.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import pe.edu.vallegrande.TranslatorTextT02.model.TranslatorText;
import pe.edu.vallegrande.TranslatorTextT02.service.TranslatorTextService;

@RestController
@RequestMapping("/api/translations")
public class TranslatorTextRest {

    private final TranslatorTextService translatorTextService;

    public TranslatorTextRest(TranslatorTextService translatorTextService) {
        this.translatorTextService = translatorTextService;
    }

    @PostMapping("/translate")
    public Mono<ResponseEntity<TranslatorText>> translateText(@RequestBody TranslatorText translation) {
        if (translation.getSourceText() == null || translation.getSourceLanguage() == null || translation.getTargetLanguage() == null) {
            return Mono.just(ResponseEntity.badRequest().build());
        }
        return translatorTextService.translateText(translation.getSourceText(), translation.getSourceLanguage(), translation.getTargetLanguage())
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
