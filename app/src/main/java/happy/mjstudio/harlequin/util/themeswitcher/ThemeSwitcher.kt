package happy.mjstudio.harlequin.util.themeswitcher

interface ThemeSwitcher {
    var mode: Mode

    fun switchMode()

    enum class Mode {
        Light, Dark
    }
}

