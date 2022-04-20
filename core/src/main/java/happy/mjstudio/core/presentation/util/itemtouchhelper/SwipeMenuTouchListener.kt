package happy.mjstudio.core.presentation.util.itemtouchhelper

import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import happy.mjstudio.core.presentation.util.debug
import kotlin.math.abs

class SwipeMenuTouchListener(
    private val menuWidth: Float, private val callback: Callback
) : OnTouchListener {
    private var dx = 0f
    private var downRawX = 0f
    private var downRawY = 0f

    override fun onTouch(view: View, e: MotionEvent): Boolean {
        when (e.actionMasked) {
            MotionEvent.ACTION_MOVE -> {
                callback.onContentXChanged(e.rawX + dx)
            }
            MotionEvent.ACTION_DOWN -> {
                dx = view.x - e.rawX
                downRawX = e.rawX
                downRawY = e.rawY
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                if (e.rawX + dx < -menuWidth) {
                    callback.onContentXAnimated(-menuWidth)
                    callback.onMenuOpened()
                } else {
                    callback.onContentXAnimated(0f)
                    callback.onMenuClosed()
                }

                val xDiff = abs(downRawX - e.rawX)
                val yDiff = abs(downRawY - e.rawY)
                if (e.actionMasked == MotionEvent.ACTION_UP && xDiff < 10 && yDiff < 10) {
                    callback.onMenuClicked()
                }
            }
        }

        return false
    }

    interface Callback {
        fun onContentXChanged(x: Float)
        fun onContentXAnimated(x: Float)
        fun onMenuOpened()
        fun onMenuClosed()
        fun onMenuClicked()
    }
}