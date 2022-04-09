package happy.mjstudio.harlequin.auth.validator

import happy.mjstudio.harlequin.util.StringValidator
import javax.inject.Inject

class NameValidator @Inject constructor() : StringValidator {
    override val errorMessage: String
        get() = "이름은 2-8자여야 합니다"

    override fun validate(str: String): Boolean {
        return str.length in 2..8
    }
}