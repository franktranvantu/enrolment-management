package com.franktran.enrolmentmanagement.exception;

import com.franktran.enrolmentmanagement.dto.ResultDto;
import com.franktran.enrolmentmanagement.dto.ResultStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class DataInValidExceptionHandler {

    @ExceptionHandler(DataInValidException.class)
    public String handleDataInValidException(DataInValidException ex,
                                             RedirectAttributes ra) {
        ResultDto result = new ResultDto();
        result.setStatus(ResultStatus.FAIL);
        result.setMessage(ex.getMessage());
        ra.addFlashAttribute("result");
        return ex.getViewName();
    }
}
