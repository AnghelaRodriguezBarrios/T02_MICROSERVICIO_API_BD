package pe.edu.vallegrande.TranslatorTextT02.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.TranslatorTextT02.model.TranslatorText;

@Repository
public interface TranslatorTextRepository extends ReactiveCrudRepository<TranslatorText, Long> {
    // Aquí agregar métodos personalizados si es necesario
}
