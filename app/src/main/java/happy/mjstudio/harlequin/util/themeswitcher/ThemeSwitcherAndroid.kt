package happy.mjstudio.harlequin.util.themeswitcher

import androidx.appcompat.app.AppCompatDelegate
import happy.mjstudio.harlequin.util.localstorage.LocalStorage
import happy.mjstudio.harlequin.util.themeswitcher.ThemeSwitcher.Mode
import happy.mjstudio.harlequin.util.themeswitcher.ThemeSwitcher.Mode.Dark
import happy.mjstudio.harlequin.util.themeswitcher.ThemeSwitcher.Mode.Light
import javax.inject.Inject

class ThemeSwitcherAndroid @Inject constructor(private val localStorage: LocalStorage) : ThemeSwitcher {
    init {
        applyModeAsTheme(mode)
    }

    override var mode: Mode
        get() = if (localStorage.loadBoolean(KEY_IS_DARK_MODE, true)) Dark else Light
        set(value) {
            localStorage.saveBoolean(KEY_IS_DARK_MODE, value == Dark)
            applyModeAsTheme(value)
        }

    override fun switchMode() {
        mode = if (mode == Dark) Light else Dark
    }

    private fun applyModeAsTheme(mode: Mode) {
        AppCompatDelegate.setDefaultNightMode(if (mode == Dark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
    }

    companion object {
        private const val KEY_IS_DARK_MODE = "darkMode"
    }
}