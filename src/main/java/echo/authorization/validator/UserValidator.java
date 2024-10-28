package echo.authorization.validator;

import echo.authorization.dto.AuthenticationPrimaryInfoDto;
import echo.authorization.dto.RegistrationInfoDto;
import echo.authorization.exception.AuthorizationErrorType;
import echo.authorization.exception.AuthorizationException;
import echo.authorization.exception.GlobalErrorType;
import echo.authorization.exception.RegistrationErrorType;
import echo.authorization.exception.RegistrationException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserValidator {

    public static void validate(RegistrationInfoDto registrationInfoDto) {
        List<GlobalErrorType> errorTypes = new ArrayList<>();
        if (isEmptyOrNull(registrationInfoDto.getLogin()) || !validateLogin(registrationInfoDto.getLogin())) {
            errorTypes.add(RegistrationErrorType.VALIDATE_LOGIN_NOT_CORRECT);
        }
        if (isEmptyOrNull(registrationInfoDto.getEmail()) || !validateEmail(registrationInfoDto.getEmail())) {
            errorTypes.add(RegistrationErrorType.VALIDATE_EMAIL_NOT_CORRECT);
        }
        if (isEmptyOrNull(registrationInfoDto.getPassword()) || !validatePassword(registrationInfoDto.getPassword())) {
            errorTypes.add(RegistrationErrorType.VALIDATE_PASSWORD_NOT_CORRECT);
        }
        if (!errorTypes.isEmpty()) {
            throw new RegistrationException(errorTypes, HttpStatus.BAD_REQUEST);
        }
    }

    public static void validate(AuthenticationPrimaryInfoDto authenticationPrimaryInfoDto) {
        List<GlobalErrorType> errorTypes = new ArrayList<>();
        if (isEmptyOrNull(authenticationPrimaryInfoDto.getLoginOrEmail())) {
            errorTypes.add(AuthorizationErrorType.LOGIN_OR_EMAIL_IS_EMPTY);
        } else {
            if (!(isEmail(authenticationPrimaryInfoDto) || isLogin(authenticationPrimaryInfoDto))) {
                errorTypes.add(AuthorizationErrorType.LOGIN_OR_EMAIL_IS_NOT_CORRECT);
            }
        }
        if (!errorTypes.isEmpty()) {
            throw new AuthorizationException(errorTypes, HttpStatus.BAD_REQUEST);
        }
    }

    public static boolean isLogin(AuthenticationPrimaryInfoDto authenticationPrimaryInfoDto) {
        return (validateLogin(authenticationPrimaryInfoDto.getLoginOrEmail()));
    }

    private static boolean isEmail(AuthenticationPrimaryInfoDto authenticationPrimaryInfoDto) {
        return validateEmail(authenticationPrimaryInfoDto.getLoginOrEmail());
    }

    public static boolean validateLogin(String login) {
        return login.matches("^[a-zA-Z][a-zA-Z0-9-_.]{3,19}$");
    }

    public static boolean validatePassword(String password) {
        return password.matches("(?=.*[0-9])(?=.*[A-Za-z])(?=.*\\W).{8,60}");
    }

    public static boolean validateEmail(String email) {
        return email.matches("^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$");
    }

    public static boolean isEmptyOrNull(String string) {
        return string == null || string.trim().isEmpty();
    }
}
