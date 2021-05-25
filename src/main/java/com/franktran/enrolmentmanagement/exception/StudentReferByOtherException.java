package com.franktran.enrolmentmanagement.exception;

public class StudentReferByOtherException extends RuntimeException {

    public StudentReferByOtherException(String name) {
        super(String.format("Student %s is being referred by others!", name));
    }
}
