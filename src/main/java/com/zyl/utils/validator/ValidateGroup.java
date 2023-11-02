package com.zyl.utils.validator;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ValidateGroup {
    ValidateField[] fields();
}
