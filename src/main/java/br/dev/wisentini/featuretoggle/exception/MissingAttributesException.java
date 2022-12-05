package br.dev.wisentini.featuretoggle.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MissingAttributesException extends RuntimeException {

    public MissingAttributesException(String message) {
        super(message);
    }
}
