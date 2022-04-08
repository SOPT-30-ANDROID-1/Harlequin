package happy.mjstudio.harlequin.util

interface StringValidator {
    val errorMessage: String?
        get() = null

    fun validate(str: String): Boolean
}