package tech.mosaleh.together.domain.use_cases

import android.util.Patterns
import tech.mosaleh.together.domain.utils.ValidationResult

class EmailValidationUseCase {

    operator fun invoke (email:String): ValidationResult {
        if (email.isBlank()){
            return ValidationResult(
                false,
                "Email can't be empty"
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return ValidationResult(
                false,
                "Email is not Valid"
            )
        }
        return ValidationResult(
            true,
        )
    }
}