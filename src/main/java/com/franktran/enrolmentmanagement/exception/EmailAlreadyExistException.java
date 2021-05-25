package com.franktran.enrolmentmanagement.exception;

public class EmailAlreadyExistException extends RuntimeException {

    public EmailAlreadyExistException(String email) {
        super(String.format("Email %s already exist!", email));
    }
}
