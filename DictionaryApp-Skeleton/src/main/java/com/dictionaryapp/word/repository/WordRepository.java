package com.dictionaryapp.word.repository;

import com.dictionaryapp.language.model.LanguageName;
import com.dictionaryapp.word.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WordRepository extends JpaRepository<Word, UUID> {
    List<Word> findAllByLanguageLanguageName(LanguageName languageName);
}
