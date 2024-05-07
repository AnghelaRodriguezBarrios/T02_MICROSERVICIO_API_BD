package pe.edu.vallegrande.TranslatorTextT02.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "translation_text")
@Getter
@Setter
public class TranslatorText {
	
	   @Id
	    @Column(value = "id")
	    private Long id;

	    @Column(value = "source_text")
	    private String sourceText;

	    @Column(value = "translated_text")
	    private String translatedText;

	    @Column(value = "source_language")
	    private String sourceLanguage;

	    @Column(value = "target_language")
	    private String targetLanguage;

	    @Column(value = "translation_time")
	    private LocalDateTime translationTime;

	    
	}
