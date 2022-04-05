package happy.mjstudio.harlequin.util.localstorage

import android.content.Context
import javax.inject.Inject

class SharedPreferencesLocalStorage @Inject constructor(context: Context) : LocalStorage {
    private val sharedPreferences = context.getSharedPreferences("hey", Context.MODE_PRIVATE)

    override fun saveString(key: String, value: String) = sharedPreferences.edit().putString(key, value).apply()
    override fun loadString(key: String) = sharedPreferences.getString(key, null)

    override fun saveBoolean(key: String, value: Boolean) = sharedPreferences.edit().putBoolean(key, value).apply()
    override fun loadBoolean(key: String, default: Boolean) = sharedPreferences.getBoolean(key, default)
}