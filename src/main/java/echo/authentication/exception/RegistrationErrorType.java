package echo.authentication.exception;

import lombok.Getter;

@Getter
public enum RegistrationErrorType implements GlobalErrorType {
    VALIDATE_LOGIN_NOT_CORRECT("Login is incorrect"),
    VALIDATE_EMAIL_NOT_CORRECT("Email is incorrect"),
    VALIDATE_PASSWORD_NOT_CORRECT("Password is incorrect"),
    LOGIN_ALREADY_EXISTS("Login already exists"),
    EMAIL_ALREADY_EXISTS("Email already exists");

    private final String message;

    RegistrationErrorType(String message) {
        this.message = message;
    }

}
