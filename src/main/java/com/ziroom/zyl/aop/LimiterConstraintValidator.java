package com.ziroom.zyl.aop;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LimiterConstraintValidator implements ConstraintValidator<Limiter, String> {

    @Override
    public void initialize(Limiter constraintAnnotation) {

    }

    @Override
    public boolean isValid(String var1, ConstraintValidatorContext var2){
        return true;
    }
}
