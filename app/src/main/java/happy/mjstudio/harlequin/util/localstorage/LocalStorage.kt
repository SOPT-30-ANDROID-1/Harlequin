package happy.mjstudio.harlequin.util.localstorage

interface LocalStorage {
    fun saveString(key: String, value: String)
    fun loadString(key: String): String?

    fun saveBoolean(key: String, value: Boolean)
    fun loadBoolean(key: String, default: Boolean = false): Boolean
}

