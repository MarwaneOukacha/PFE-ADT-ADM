package ma.adria.document_validation.administration.exception.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import ma.adria.document_validation.administration.exception.*;
import ma.adria.document_validation.administration.exception.KeycloakException;
import ma.adria.document_validation.administration.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class globalExceptionHandler {

    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidUserDataExceptions(UserException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", ex.getMessage());
        return errorMap;
    }

    @ExceptionHandler(KeycloakException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleKycloakException(KeycloakException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", ex.getMessage());
        return errorMap;
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidEmailException(NoSuchElementException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", "Invalid email");
        return errorMap;
    }

    @ExceptionHandler(GenericException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleGenericException(GenericException ex) {
        Map<String,String> errorMap=new HashMap<>();
        errorMap.put("error", ex.getMessage());
        return errorMap;
    }
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        Map<String,String> errorMap=new HashMap<>();
        errorMap.put("error", ex.getMessage());
        return errorMap;
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        Map<String,String> errorMap=new HashMap<>();
        errorMap.put("error", ex.getMessage());
        return errorMap;
    }
}
