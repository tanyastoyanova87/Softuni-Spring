package com.dictionaryapp.web.dto;

import com.dictionaryapp.language.model.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class AddWordRequest {

    @NotBlank
    @Size(min = 2, max = 40, message = "The term length must be between 2 and 40 characters!")
    private String term;

    @NotBlank
    @Size(min = 2, max = 80, message = "The translation length must be between 2 and 80 characters!")
    private String translation;

    @Size(min = 2, max = 200, message = "The example length must be between 2 and 200 characters!")
    private String example;

    @NotNull
    @PastOrPresent(message = "The input date must be in the past or present!")
    private LocalDate inputDate;

    @NotNull(message = "You must select a language!")
    private Language language;
}
