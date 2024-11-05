package echo.authentication.service;

import echo.authentication.exception.GlobalErrorType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ErrorResponse {
    private List<GlobalErrorType> errorType;
    private List<String> errorMessage = new ArrayList<>();

    public ErrorResponse(List<GlobalErrorType> errorTypes) {
        this.errorType = errorTypes;
        for(GlobalErrorType errorType : errorTypes) {
            this.errorMessage.add(errorType.getMessage());
        }
    }
}
