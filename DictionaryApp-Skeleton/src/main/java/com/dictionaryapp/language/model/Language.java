package com.dictionaryapp.language.model;

import com.dictionaryapp.word.model.Word;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private LanguageName languageName;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "language", fetch = FetchType.EAGER)
    private List<Word> words = new ArrayList<>();
}
