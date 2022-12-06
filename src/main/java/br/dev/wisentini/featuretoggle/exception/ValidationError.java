package br.dev.wisentini.featuretoggle.exception;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;

import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;

@Getter
@JsonPropertyOrder(value = {"field", "message"})
public class ValidationError extends Error {

    private final String field;

    public ValidationError(String field, String message) {
        super(message);
        this.field = field;
    }

    public ValidationError(ConstraintViolation<?> constraintViolation) {
        this(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
    }

    public ValidationError(FieldError fieldError) {
        this(fieldError.getField(), fieldError.getDefaultMessage());
    }
}
