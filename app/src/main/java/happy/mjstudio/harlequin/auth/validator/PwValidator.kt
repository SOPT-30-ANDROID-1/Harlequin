package happy.mjstudio.harlequin.auth.validator

import happy.mjstudio.harlequin.util.StringValidator
import javax.inject.Inject

class PwValidator @Inject constructor() : StringValidator {
    override val errorMessage: String?
        get() = "비밀번호는 알파벳과 숫자로 이루어진 2-8 길이의 문자열이여야 합니다"

    override fun validate(str: String): Boolean {
        return "^[\\w\\d]{2,8}$".toRegex().matches(str)
    }
}