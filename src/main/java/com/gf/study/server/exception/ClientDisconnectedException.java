package com.gf.study.server.exception;

public class ClientDisconnectedException extends Exception {
    public ClientDisconnectedException() {
        super("The client has disconnected");
    }

    public ClientDisconnectedException(String message) {
        super(message);
    }
}
