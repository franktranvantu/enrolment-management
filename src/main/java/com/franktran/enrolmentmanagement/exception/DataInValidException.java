package com.franktran.enrolmentmanagement.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataInValidException extends RuntimeException {

    private String viewName;

    public DataInValidException(String message, String viewName) {
        super(message);
        this.viewName = viewName;
    }
}
