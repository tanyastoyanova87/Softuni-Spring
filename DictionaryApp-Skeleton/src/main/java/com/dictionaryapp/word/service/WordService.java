package com.dictionaryapp.word.service;

import com.dictionaryapp.language.model.Language;
import com.dictionaryapp.language.model.LanguageName;
import com.dictionaryapp.language.service.LanguageService;
import com.dictionaryapp.user.model.User;
import com.dictionaryapp.web.dto.AddWordRequest;
import com.dictionaryapp.word.model.Word;
import com.dictionaryapp.word.repository.WordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WordService {

    private final WordRepository wordRepository;
    private final LanguageService languageService;

    public WordService(WordRepository wordRepository, LanguageService languageService) {
        this.wordRepository = wordRepository;
        this.languageService = languageService;
    }

    public List<Word> getAllWords() {
        return this.wordRepository.findAll();
    }

    public void addWord(AddWordRequest addWordRequest, User user) {
        List<Language> languages = this.languageService.addLanguages();
        Optional<Language> language = getWantedLanguage(addWordRequest, languages);

        Word word = Word.builder()
                .term(addWordRequest.getTerm())
                .translation(addWordRequest.getTranslation())
                .example(addWordRequest.getExample())
                .inputDate(addWordRequest.getInputDate())
                .language(language.get())
                .addedBy(user)
                .build();

        this.wordRepository.save(word);
    }

    private Optional<Language> getWantedLanguage(AddWordRequest addWordRequest, List<Language> languages) {
        return languages.stream().filter((l) -> l.getLanguageName()
                .equals(addWordRequest.getLanguage().getLanguageName()))
                .findFirst();
    }

    public List<Word> findAllWordsByLanguage(LanguageName languageName) {
        return this.wordRepository.findAllByLanguageLanguageName(languageName);
    }

    public void removeWordById(UUID id) {
        this.wordRepository.deleteById(id);
    }

    public void removeAllWords() {
        this.wordRepository.deleteAll();
    }
}
