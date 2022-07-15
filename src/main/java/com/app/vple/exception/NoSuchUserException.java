package com.app.vple.exception;

import lombok.Getter;

public class NoSuchUserException extends Exception {

    @Getter
    private final int ERR_CODE;

    NoSuchUserException (String message) {
        this(message, 100);
    }

    NoSuchUserException (String message, int errCode) {
        super(message);
        ERR_CODE = errCode;
    }


}
