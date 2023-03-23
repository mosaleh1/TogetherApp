package tech.mosaleh.together.domain.use_cases

import tech.mosaleh.together.domain.utils.ValidationResult

class PasswordValidationUseCase {

    operator fun invoke (password:String): ValidationResult {
        if (password.length < 8){
            return ValidationResult(
                false,
                "Password length should be greater than 8"
            )
        }
        val containsLetterAndDigit =
            password.any { it.isLowerCase() }
                && password.any { it.isUpperCase() }
                    && password.any { it.isDigit() }
        if (containsLetterAndDigit)
        {
            return ValidationResult(
                false,
                "Password should contain at least 1 lower case letter and 1 upper case and numbers"
            )
        }
        return ValidationResult(true)
    }
}