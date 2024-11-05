package com.wazzups.ftwitterbackend.exceptions;

public class IncorrectVerificationCodeException extends RuntimeException {

    public IncorrectVerificationCodeException() {
        super("Incorrect verification code");
    }
}
