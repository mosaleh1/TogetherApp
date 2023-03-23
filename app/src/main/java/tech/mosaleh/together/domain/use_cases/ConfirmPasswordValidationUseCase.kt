package tech.mosaleh.together.domain.use_cases

import tech.mosaleh.together.domain.utils.ValidationResult

class ConfirmPasswordValidationUseCase {

    operator fun invoke (password:String, repeatedPassword:String): ValidationResult {
        if (repeatedPassword.isEmpty()){
            return ValidationResult(
                false,
                "this field can't be empty"
            )
        }

        if(password != repeatedPassword){
            return ValidationResult(
                false,
                "Passwords doesn't match"
            )
        }
        return ValidationResult(true)
    }
}