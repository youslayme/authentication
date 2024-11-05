package echo.authentication.controller.handler;

import echo.authentication.exception.GlobalException;
import echo.authentication.service.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(GlobalException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getErrorTypes()), e.getHttpStatus());
    }
}
