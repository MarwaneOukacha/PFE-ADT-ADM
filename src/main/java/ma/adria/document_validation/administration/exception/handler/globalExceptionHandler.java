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

   @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public Map<String, String> handleExceptions(Exception ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", ex.getMessage());
        return errorMap;
    }
}
