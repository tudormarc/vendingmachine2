package com.tudormarc.vendingmachine2.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.util.List;

public class CentsValidator implements
        ConstraintValidator<ValidCents, BigDecimal> {
    private final List<Integer> availableCents = List.of(5, 10, 20, 50, 100);

    @Override
    public void initialize(ValidCents cents) {
    }

    @Override
    public boolean isValid(BigDecimal cents, ConstraintValidatorContext context) {
        return cents != null && availableCents.contains(cents.intValue());
    }

}
