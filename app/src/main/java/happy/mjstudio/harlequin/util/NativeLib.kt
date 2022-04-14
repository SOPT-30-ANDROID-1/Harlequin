package happy.mjstudio.harlequin.util

object NativeLib {
    init {
        System.loadLibrary("math_made_world")
    }

    external fun prime_factorize_zirige_fast(n: Long): String
}