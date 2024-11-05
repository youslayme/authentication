package echo.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Представление основных пользовательских данных из базы данных")
@Getter
@Setter
public class MainUserInfo {

    @Schema(description = "Логин")
    private String login;

    @Schema(description = "Захэшированный с помощью SHA-1 и соли пароль")
    private String password;

    @Schema(description = "Название роли")
    private String roleName;
}
