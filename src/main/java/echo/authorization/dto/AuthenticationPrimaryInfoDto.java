package echo.authorization.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationPrimaryInfoDto {
    private String loginOrEmail;
    private String password;

    public AuthenticationPrimaryInfoDto(String loginOrEmail, String password) {
        this.loginOrEmail = loginOrEmail;
        this.password = password;
    }
}
