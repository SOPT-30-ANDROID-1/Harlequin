package happy.mjstudio.harlequin.util

import android.util.Log

fun debug(vararg arg: Any?) {
    Log.e("debug", arg.toString())
}