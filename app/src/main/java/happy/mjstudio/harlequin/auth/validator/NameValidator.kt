package happy.mjstudio.harlequin.auth.validator

import happy.mjstudio.harlequin.util.StringValidator
import javax.inject.Inject

class NameValidator @Inject constructor() : StringValidator {
    override val errorMessage: String
        get() = "이름은 \"MJISGOOD\"만 허용됩니다"

    override fun validate(str: String): Boolean {
        return str.trim().uppercase() == "MJISGOOD"
    }
}