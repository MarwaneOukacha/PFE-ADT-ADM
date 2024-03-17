package ma.adria.document_validation.administration.exception;

import lombok.Getter;
import ma.adria.document_validation.administration.model.enums.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public class GenericException extends RuntimeException {

    protected final ErrorCode errorCode;

    protected HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public GenericException(ErrorCode errorCode) {
        super(errorCode.toString());
        this.errorCode = errorCode;
    }

    public GenericException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.toString(), cause);
        this.errorCode = errorCode;
    }

    public GenericException(ErrorCode errorCode, HttpStatus status) {
        super(errorCode.toString());
        this.errorCode = errorCode;
        this.httpStatus = status;
    }

}
