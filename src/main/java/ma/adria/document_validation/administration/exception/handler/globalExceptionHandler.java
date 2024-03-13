package ma.adria.document_validation.administration.exception.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ma.adria.document_validation.administration.exception.KycException;
import ma.adria.document_validation.administration.exception.UserException;

@RestControllerAdvice
public class globalExceptionHandler {
	@ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidUserDataExceptions(UserException ex) {
		Map<String,String> errorMap=new HashMap<>();
		errorMap.put("error", ex.getMessage());
		return errorMap;
    }
    
    @ExceptionHandler(KycException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleKycloakException(KycException ex) {
		Map<String,String> errorMap=new HashMap<>();
		errorMap.put("error", ex.getMessage());
		return errorMap;
    }
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidEmailException(NoSuchElementException ex) {
		Map<String,String> errorMap=new HashMap<>();
		errorMap.put("error", "Invalid email");
		return errorMap;
    }
}
