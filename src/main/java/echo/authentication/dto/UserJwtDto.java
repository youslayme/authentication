package echo.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Представление пользовательских данных пользователя для создания JWT-токена")
public record UserJwtDto(

        @Schema(description = "Логин")
        String login,

        @Schema(description = "Название роли")
        UserRole role
) {
}
