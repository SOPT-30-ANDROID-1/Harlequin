package happy.mjstudio.harlequin.auth.validator

import happy.mjstudio.harlequin.util.StringValidator
import javax.inject.Inject
import javax.inject.Named

class AuthFormValidatorImpl @Inject constructor(
    @Named("id") private val idValidator: StringValidator,
    @Named("pw") private val pwValidator: StringValidator,
    @Named("name") private val nameValidator: StringValidator
) : AuthFormValidator {
    override fun validateId(str: String, onFail: (errorMessage: String) -> Unit): Boolean {
        if (!idValidator.validate(str)) {
            onFail(idValidator.errorMessage!!)
            return false
        }
        return true
    }

    override fun validatePw(str: String, onFail: (errorMessage: String) -> Unit): Boolean {
        if (!pwValidator.validate(str)) {
            onFail(pwValidator.errorMessage!!)
            return false
        }
        return true
    }

    override fun validateName(str: String, onFail: (errorMessage: String) -> Unit): Boolean {
        if (!nameValidator.validate(str)) {
            onFail(nameValidator.errorMessage!!)
            return false
        }
        return true
    }
}