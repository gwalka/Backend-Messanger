package basic.main.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRegisterDto(
        @NotBlank(message = "Password can't be blank")
        @Size(min = 8, message = "Password must have at least 8 symbols")
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
                message = "Пароль должен содержать хотя бы одну цифру, одну заглавную букву и один спецсимвол"
        )
        String password,

        @NotBlank(message = "Email can't be blank")
        @Pattern(
                regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
                message = "Email must contain only Latin letters, digits, and valid symbols"
        )
        String email,

        @NotBlank(message = "Username can't be blank")
        @Size(min = 6, message = "Username must have at least 6 symbols")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username must contain only letters and digits")
        String userName
) {
}
