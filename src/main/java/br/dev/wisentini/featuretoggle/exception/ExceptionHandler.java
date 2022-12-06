package br.dev.wisentini.featuretoggle.exception;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {ResourceNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleResourceNotFoundException(
        ResourceNotFoundException resourceNotFoundException
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        ErrorResponse errorResponse = new ErrorResponse(
            new Error(resourceNotFoundException.getMessage()),
            status
        );

        return new ResponseEntity<>(errorResponse, status);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {MissingFieldsException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleNoArgumentException(
        MissingFieldsException missingFieldsException
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorResponse errorResponse = new ErrorResponse(
            new Error(missingFieldsException.getMessage()),
            status
        );

        return new ResponseEntity<>(errorResponse, status);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleConstraintValidationException(
        ConstraintViolationException constraintViolationException
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorResponse errorResponse = new ErrorResponse(
            constraintViolationException
                .getConstraintViolations()
                .stream()
                .map(ValidationError::new)
                .collect(Collectors.toSet()),
            status
        );

        return new ResponseEntity<>(errorResponse, status);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException methodArgumentNotValidException
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorResponse errorResponse = new ErrorResponse(
            methodArgumentNotValidException
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ValidationError::new)
                .collect(Collectors.toSet()),
            status
        );

        return new ResponseEntity<>(errorResponse, status);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = BindException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleBindException(BindException bindException) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorResponse errorResponse = new ErrorResponse(
            bindException
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ValidationError::new)
                .collect(Collectors.toSet()),
            status
        );

        return new ResponseEntity<>(errorResponse, status);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = ConversionFailedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleConversionFailed(
        ConversionFailedException conversionFailedException
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorResponse errorResponse = new ErrorResponse(
            new ValidationError(
                Objects.requireNonNull(conversionFailedException.getValue()).toString(),
                conversionFailedException.getCause().getMessage()
            ),
            status
        );

        return new ResponseEntity<>(errorResponse, status);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(
        HttpMessageNotReadableException httpMessageNotReadableException
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorResponse errorResponse = new ErrorResponse(
            new Error(httpMessageNotReadableException.getMessage()),
            status
        );

        return new ResponseEntity<>(errorResponse, status);
    }
}
