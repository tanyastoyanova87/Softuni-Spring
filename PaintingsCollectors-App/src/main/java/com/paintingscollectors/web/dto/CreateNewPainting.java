package com.paintingscollectors.web.dto;

import com.paintingscollectors.painting.model.Style;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Builder
@Data
public class CreateNewPainting {

    @NotNull
    @Size(min = 5, max = 40, message = "Name length must be between 5 and 40 characters!")
    private String name;

    @NotNull
    @Size(min = 5, max = 40, message = "Author name must be between 5 and 30 characters!")
    private String author;

    @NotNull
    @URL(message = "Please enter valid image URL!")
    private String imageUrl;

    @NotNull(message = "You must select a style!")
    private Style style;
}
