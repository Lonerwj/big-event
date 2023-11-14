package com.itheima.anno;

import com.itheima.Validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = {StateValidation.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface State {

    String message() default "{发布状态有误}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
