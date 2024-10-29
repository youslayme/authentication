package echo.authorization.controller;

import echo.authorization.dto.AuthenticationPrimaryInfoDto;
import echo.authorization.dto.RegistrationInfoDto;
import echo.authorization.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(value = "/signin")
    @ResponseStatus(HttpStatus.CREATED)
    public void registration(@RequestBody RegistrationInfoDto registrationInfoDTO) {
        userService.registerNewAccount(registrationInfoDTO);
    }

    @PostMapping(value = "/login")
    @ResponseStatus(HttpStatus.OK)
    public String authentication(@RequestBody AuthenticationPrimaryInfoDto authorizationInfoDTO) {
        return userService.authenticate(authorizationInfoDTO);
    }
}
