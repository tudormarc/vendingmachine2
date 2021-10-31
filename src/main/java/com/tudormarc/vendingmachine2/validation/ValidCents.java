package com.tudormarc.vendingmachine2.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CentsValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCents {
    String message() default "Invalid cents";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
