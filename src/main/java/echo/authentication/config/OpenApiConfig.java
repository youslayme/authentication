package echo.authentication.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "User Authentication API",
                description = "API для регистрации и аутентификации пользователей с помощью JWT-токенов",
                version = "1.0.0",
                contact = @Contact(
                        name = "Chardona",
                        email = "dvalera01@gmail.com"
                )
        )
)
@SecurityScheme(
        name = "JWT",
        type = SecuritySchemeType.HTTP
)
public class OpenApiConfig {

}