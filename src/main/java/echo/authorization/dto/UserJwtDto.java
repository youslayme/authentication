package echo.authorization.dto;

public record UserJwtDto(
        String login,
        UserRole role
) {
}
