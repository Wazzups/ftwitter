package com.wazzups.ftwitterbackend.exceptions;

public class EmailFailedToSendException extends RuntimeException {

    public EmailFailedToSendException() {
        super("There was an error sending the e-mail.");
    }
}
