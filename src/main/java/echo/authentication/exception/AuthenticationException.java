package echo.authentication.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class AuthenticationException extends GlobalException {

    public AuthenticationException(List<GlobalErrorType> errorTypes, HttpStatus httpStatus) {
        super(errorTypes);
        this.httpStatus = httpStatus;
        this.errorTypes = errorTypes;
    }
    public AuthenticationException(GlobalErrorType errorType, HttpStatus httpStatus) {
        super(List.of(errorType));
        this.httpStatus = httpStatus;
        this.errorTypes = List.of(errorType);
    }
}
