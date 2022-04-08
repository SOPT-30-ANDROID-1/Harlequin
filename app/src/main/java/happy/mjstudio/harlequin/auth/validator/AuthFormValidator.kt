package happy.mjstudio.harlequin.auth.validator

interface AuthFormValidator {
    fun validateId(str: String, onFail: (errorMessage: String) -> Unit): Boolean
    fun validatePw(str: String, onFail: (errorMessage: String) -> Unit): Boolean
    fun validateName(str: String, onFail: (errorMessage: String) -> Unit): Boolean
}

