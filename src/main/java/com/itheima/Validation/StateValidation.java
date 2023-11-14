package com.itheima.Validation;

import com.itheima.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State,String> {//第一个参数为提供校验规则的类，第二个参数为提供校验参数的属性
    @Override
    public boolean isValid(String state, ConstraintValidatorContext constraintValidatorContext) {
        if (state.equals("已发布")||state.equals("草稿")){
            return true;
        }
        return false;
    }
}
