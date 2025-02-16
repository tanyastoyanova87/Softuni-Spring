package com.dictionaryapp.language.service;

import com.dictionaryapp.language.model.Language;
import com.dictionaryapp.language.model.LanguageName;
import com.dictionaryapp.language.repository.LanguageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {

    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public List<Language> addLanguages() {
        if (this.languageRepository.count() == 0) {
            Language french = initializaLanguage(LanguageName.FRENCH, "A Romance language spoken worldwide, " +
                    "known for its elegance and cultural richness. It's the official language of France " +
                    "and numerous nations, famed for its cuisine, art, and literature.");

            Language german = initializaLanguage(LanguageName.GERMAN, "A West Germanic language, is spoken by " +
                    "over 90 million people worldwide. Known for its complex grammar and compound words, " +
                    "it's the official language of Germany and widely used in Europe.");

            Language spanish = initializaLanguage(LanguageName.SPANISH, "A Romance language, is spoken by over" +
                    " 460 million people worldwide. It boasts a rich history, diverse dialects, and is " +
                    "known for its melodious sound, making it a global cultural treasure.");

            Language italian = initializaLanguage(LanguageName.ITALIAN, "A Romance language spoken in" +
                    " Italy and parts of Switzerland, with rich cultural heritage. " +
                    "Known for its melodious sounds, it's a gateway to Italian art, cuisine, and history.");

            this.languageRepository.save(french);
            this.languageRepository.save(german);
            this.languageRepository.save(spanish);
            this.languageRepository.save(italian);
        }
        return this.languageRepository.findAll();
    }

    private Language initializaLanguage(LanguageName languageName, String description) {
        return Language.builder()
                .languageName(languageName)
                .description(description)
                .build();
    }
}
