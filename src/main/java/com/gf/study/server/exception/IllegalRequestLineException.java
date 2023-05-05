package com.gf.study.server.exception;

public class IllegalRequestLineException extends Exception {
    public IllegalRequestLineException() {
        super("Illegal request exception.");
    }

    public IllegalRequestLineException(String message) {
        super(message);
    }
}
