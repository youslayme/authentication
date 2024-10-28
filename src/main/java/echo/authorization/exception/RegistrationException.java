package echo.authorization.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class RegistrationException extends GlobalException {

    public RegistrationException(List<GlobalErrorType> errorTypes, HttpStatus httpStatus) {
        super(errorTypes);
        this.httpStatus = httpStatus;
        this.errorTypes = errorTypes;
    }
}
