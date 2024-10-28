package echo.authorization.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationInfoDto {
    private String email;
    private String login;
    private String password;

    public RegistrationInfoDto(String email, String login, String password) {
        this.email = email;
        this.login = login;
        this.password = password;
    }
}
