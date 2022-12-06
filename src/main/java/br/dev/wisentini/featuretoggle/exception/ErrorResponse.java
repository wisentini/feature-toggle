package br.dev.wisentini.featuretoggle.exception;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import java.util.Set;

@Getter
@AllArgsConstructor
@JsonPropertyOrder(value = {"status", "errors", "timestamp"})
public class ErrorResponse {

    private final Set<Error> errors;
    private final HttpStatus status;
    private final LocalDateTime timestamp;

    public ErrorResponse(Set<Error> errors, HttpStatus status) {
        this(errors, status, LocalDateTime.now());
    }
}
