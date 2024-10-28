package echo.authorization.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public abstract class GlobalException extends RuntimeException {
    List<GlobalErrorType> errorTypes;
    HttpStatus httpStatus;

    public GlobalException(List<GlobalErrorType> errorTypes) {
        super(errorTypes.stream().map(GlobalErrorType::getMessage).collect(Collectors.joining(".")));
    }
}
