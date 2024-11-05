package echo.authentication.exception;

import lombok.Getter;

@Getter
public enum AuthenticationErrorType implements GlobalErrorType {
    PASSWORD_NOT_CORRECT("Password is incorrect"),
    LOGIN_OR_EMAIL_IS_EMPTY("Login or email is empty"),
    LOGIN_OR_EMAIL_IS_NOT_CORRECT("Login or email is not correct"),
    LOGIN_DOES_NOT_EXISTS("Login does not exist"),
    EMAIL_DOES_NOT_EXISTS("Email does not exist");

    private final String message;

    AuthenticationErrorType(String message) {
        this.message = message;
    }

}
