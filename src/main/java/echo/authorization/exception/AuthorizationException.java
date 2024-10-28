package echo.authorization.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class AuthorizationException extends GlobalException {

    public AuthorizationException(List<GlobalErrorType> errorTypes, HttpStatus httpStatus) {
        super(errorTypes);
        this.httpStatus = httpStatus;
        this.errorTypes = errorTypes;
    }
    public AuthorizationException(GlobalErrorType errorType, HttpStatus httpStatus) {
        super(List.of(errorType));
        this.httpStatus = httpStatus;
        this.errorTypes = List.of(errorType);
    }
}
