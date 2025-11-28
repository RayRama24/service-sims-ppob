package test.praktek.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationRequestDTO {

    @Email(message = "Parameter email tidak sesuai format")
    @NotBlank
    private String email;

    @NotBlank
    private String first_name;

    @NotBlank
    private String last_name;

    @Size(min = 8, message = "Password minimal 8 karakter")
    private String password;
}
