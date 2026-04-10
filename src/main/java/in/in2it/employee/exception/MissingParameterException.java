package in.in2it.employee.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MissingParameterException extends RuntimeException {

    private HttpStatus status;

    public MissingParameterException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }
}
