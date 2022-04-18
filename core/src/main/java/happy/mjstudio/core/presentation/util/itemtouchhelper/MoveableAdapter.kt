package happy.mjstudio.core.presentation.util.itemtouchhelper

fun interface MoveableAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)
}