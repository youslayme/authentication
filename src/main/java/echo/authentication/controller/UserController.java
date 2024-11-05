package echo.authentication.controller;

import echo.authentication.dto.AuthenticationPrimaryInfoDto;
import echo.authentication.dto.RegistrationInfoDto;
import echo.authentication.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Контроллер для манипуляций с аккаунтами пользователей",
        description = "Эндпоинты для регистрации и аутентификации пользователей"
)
@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signin")
    @Operation(
            summary = "Регистрация нового пользователя",
            description = "Создает нового пользователя"
    )
    @SecurityRequirement(name = "JWT")
    @ResponseStatus(HttpStatus.CREATED)
    public void registration(@RequestBody RegistrationInfoDto registrationInfoDTO) {
        userService.registerNewAccount(registrationInfoDTO);
    }

    @PostMapping("/login")
    @Operation(
            summary = "Аутентификация пользователя",
            description = "Производит проверку подлинности пользователя"
    )
    @SecurityRequirement(name = "JWT")
    @ResponseStatus(HttpStatus.OK)
    public String authentication(@RequestBody AuthenticationPrimaryInfoDto authenticationInfoDTO) {
        return userService.authenticate(authenticationInfoDTO);
    }
}
