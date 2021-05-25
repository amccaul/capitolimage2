package com.example.capitol.viewmodel
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class PasswordMatchesValidator : ConstraintValidator<PasswordMatches, NewUserViewModel?> {

    override fun isValid(value: NewUserViewModel?, context: ConstraintValidatorContext?): Boolean {
        return value?.password.equals(value?.matchingPassword)
    }
}
