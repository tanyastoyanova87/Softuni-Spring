package com.dictionaryapp.word.model;

import com.dictionaryapp.language.model.Language;
import com.dictionaryapp.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String term;

    @Column(nullable = false)
    private String translation;

    private String example;

    @Column(nullable = false)
    private LocalDate inputDate;

    @ManyToOne
    private Language language;

    @ManyToOne
    private User addedBy;
}
