package com.lvargese.courseapi.exception;

public class InvalidRefreshTokenException extends Throwable {
    public InvalidRefreshTokenException(String msg) {
        super(msg);
    }
}
