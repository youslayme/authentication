package echo.authorization.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainUserInfo {
    private String login;
    private String password;
    private String roleName;
}
