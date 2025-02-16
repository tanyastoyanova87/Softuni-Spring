package app.web.dto;

import app.story.model.Kind;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class AddStoryRequest {

    @Size(min = 5, max = 25, message = "Title length must be between 5 and 25 characters!")
    private String title;

    @Size(min = 10, max = 1000, message = "Description length must be between 10 and 1000 characters!")
    private String description;

    @NotNull(message = "You must select an encounter kind!")
    private Kind kind;

    @NotNull(message = "must not be null")
    private LocalDate date;
}
