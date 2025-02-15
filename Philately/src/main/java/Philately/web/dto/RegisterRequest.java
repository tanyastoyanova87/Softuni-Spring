package Philately.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegisterRequest {

    @NotBlank(message = "Username cannot be empty!")
    @Size(min = 3, max = 20, message = "Username length must be between 3 and 20 characters!")
    private String username;

    @NotBlank(message = "Email cannot be empty!")
    private String email;

    @NotBlank(message = "Password cannot be empty!")
    @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 characters!")
    private String password;

    @NotBlank(message = "Confirm password cannot be empty!")
    @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 characters!")
    private String confirmPassword;
}
