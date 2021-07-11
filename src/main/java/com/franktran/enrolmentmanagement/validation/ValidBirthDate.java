package com.franktran.enrolmentmanagement.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidBirthDateValidator.class)
public @interface ValidBirthDate {
  String message() default "Date is invalid";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
