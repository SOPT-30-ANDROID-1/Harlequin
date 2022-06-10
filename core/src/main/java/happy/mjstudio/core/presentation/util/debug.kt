package happy.mjstudio.core.presentation.util

import android.util.Log

fun debug(vararg args: Any?) {
    var str = ""
    args.forEachIndexed { i, j ->
        str += j?.toString()
        if (i < args.size - 1) str += "\n"
    }
    Log.e("debug", str);
}