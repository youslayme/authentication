package echo.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Представление пользовательских данных для регистрации")
@Getter
@Setter
public class RegistrationInfoDto {

    @Schema(description = "Электронная почта",
            pattern = "^(?=.{6,60}$)[a-zA-Z0-9._%+-]{1,50}@[a-zA-Z0-9.-]{1,50}\\.[a-zA-Z]{2,4}$")
    private String email;

    @Schema(description = "Логин",
            pattern = "^[a-zA-Z][a-zA-Z0-9-_.]{3,19}$")
    private String login;

    @Schema(description = "Захэшированный с помощью SHA-1 и соли пароль",
            pattern = "(?=.*[0-9])(?=.*[A-Za-z])(?=.*\\W).{8,60}")
    private String password;

    public RegistrationInfoDto(String email, String login, String password) {
        this.email = email;
        this.login = login;
        this.password = password;
    }
}
