package echo.authorization.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationSecondaryInfoDto {
    private String login;
    private String email;
    private String password;

    public AuthenticationSecondaryInfoDto(String email, String login, String password) {
        this.email = email;
        this.login = login;
        this.password = password;
    }
}
