package echo.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Представление первичных пользовательских данных при аутентификации")
@Getter
@Setter
public class AuthenticationPrimaryInfoDto {

    @Schema(description = """
            Логин либо имейл. Должно соответствовать одному из двух паттернов:
            логин ^[a-zA-Z][a-zA-Z0-9-_.]{3,19}$ либо имэйл ^(?=.{6,60}$)[a-zA-Z0-9._%+-]{1,50}@[a-zA-Z0-9.-]{1,50}\\.[a-zA-Z]{2,4}$
            """)
    private String loginOrEmail;

    @Schema(
            description = "Пароль",
            pattern = "(?=.*[0-9])(?=.*[A-Za-z])(?=.*\\W).{8,60}"
    )
    private String password;

    public AuthenticationPrimaryInfoDto(String loginOrEmail, String password) {
        this.loginOrEmail = loginOrEmail;
        this.password = password;
    }
}
