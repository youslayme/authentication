package echo.authorization.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class User {
    private String firstName;
    private String surname;
    private String login;
    private String password;
    private String email;
    private Date registrationDate;
    private String roleName;
}
