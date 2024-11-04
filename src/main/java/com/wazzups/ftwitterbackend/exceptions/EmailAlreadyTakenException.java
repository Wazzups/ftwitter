package com.wazzups.ftwitterbackend.exceptions;

public class EmailAlreadyTakenException extends RuntimeException {

    private final long serialVersionUUID = 1L;

    public EmailAlreadyTakenException() {
        super("Email is already taken");
    }
}
