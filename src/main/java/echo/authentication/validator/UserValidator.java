package echo.authentication.validator;

import echo.authentication.dto.AuthenticationPrimaryInfoDto;
import echo.authentication.dto.RegistrationInfoDto;
import echo.authentication.exception.AuthenticationErrorType;
import echo.authentication.exception.AuthenticationException;
import echo.authentication.exception.GlobalErrorType;
import echo.authentication.exception.RegistrationErrorType;
import echo.authentication.exception.RegistrationException;
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
            errorTypes.add(AuthenticationErrorType.LOGIN_OR_EMAIL_IS_EMPTY);
        } else {
            if (!(isEmail(authenticationPrimaryInfoDto) || isLogin(authenticationPrimaryInfoDto))) {
                errorTypes.add(AuthenticationErrorType.LOGIN_OR_EMAIL_IS_NOT_CORRECT);
            }
        }
        if (!errorTypes.isEmpty()) {
            throw new AuthenticationException(errorTypes, HttpStatus.BAD_REQUEST);
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
        return email.matches("^(?=.{6,60}$)[a-zA-Z0-9._%+-]{1,50}@[a-zA-Z0-9.-]{1,50}\\.[a-zA-Z]{2,4}$");
    }

    public static boolean isEmptyOrNull(String string) {
        return string == null || string.trim().isEmpty();
    }
}
