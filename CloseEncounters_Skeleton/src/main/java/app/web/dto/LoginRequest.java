package app.web.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginRequest {

    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters!")
    private String username;

    @Size(min = 4, max = 20, message = "Password must be between 4 and 20 characters!")
    private String password;
}
