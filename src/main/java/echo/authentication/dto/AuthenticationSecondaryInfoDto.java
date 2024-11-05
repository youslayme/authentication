package echo.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Представление пользовательских данных при аутентификации после определения введенных данных поля login/email")
@Getter
@Setter
public class AuthenticationSecondaryInfoDto {

    @Schema(description = "Логин")
    private String login;

    @Schema(description = "Имейл")
    private String email;

    @Schema(description = "Пароль")
    private String password;

    public AuthenticationSecondaryInfoDto(String email, String login, String password) {
        this.email = email;
        this.login = login;
        this.password = password;
    }
}
