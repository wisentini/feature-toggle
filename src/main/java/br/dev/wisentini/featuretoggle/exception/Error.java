package br.dev.wisentini.featuretoggle.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Error {

    protected final String message;
}
