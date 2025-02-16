package app.web.dto;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegisterRequest {

    @Size(min = 4, max = 20, message = "Username must be at least 4 characters")
    private String username;

    @Size(min = 4, max = 20, message = "Password must be at least 4 characters")
    private String password;
}
