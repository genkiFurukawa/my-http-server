package com.gf.study.server.exception;

public class MethodNotAllowedException extends Exception {
    public MethodNotAllowedException() {
        super("HTTP 405 Method Not Allowed");
    }

    public MethodNotAllowedException(String message) {
        super(message);
    }
}
