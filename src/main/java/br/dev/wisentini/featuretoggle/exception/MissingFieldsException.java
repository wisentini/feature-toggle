package br.dev.wisentini.featuretoggle.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MissingFieldsException extends RuntimeException {

    public MissingFieldsException(String message) {
        super(message);
    }
}
