# Harlequin

> 내가 사랑하는 남자가 완전히 미친놈이니까 나도 미쳐야겠네.

<img src="https://user-images.githubusercontent.com/33388801/161442672-8605c571-b0d1-4d28-af8f-79a06ddd7ee0.png" width=250/>

# Seminar 2

## RecyclerView

<img src="https://user-images.githubusercontent.com/33388801/164178969-71919141-3fed-45d3-b3f9-9a84e1491899.gif" width=300/>

1. `RecyclerView` 를 사용하여 리스트를 구현했습니다.

거대하고 더러운 Adapter가 만들어졌습니다.

GithubProfileAdapter.kt
```kotlin
class GithubProfileAdapter(
    private val dataFlow: StateFlow<List<GithubProfile>>,
    private val menuOpenStateFlow: StateFlow<List<Boolean>>,
    private val lifecycle: Lifecycle,
    private val pixelRatio: PixelRatio,
    private val onItemMoveCallback: (Int, Int) -> Unit,
    private val onItemRemoved: (Int) -> Unit,
    private val onItemMenuOpened: (Int) -> Unit,
    private val onItemMenuClosed: (Int) -> Unit,
    private val onItemClicked: (GithubProfile, View) -> Unit,
) : RecyclerView.Adapter<GithubProfileAdapter.GithubFollowerHolder>(), MoveableAdapter {...
```

`setOnTouchListener` 로 터치이벤트를 받아서 메뉴 스와이핑을 구현했습니다.
```kotlin
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
```


2. `MotionLayout`과 `ImageFilterView` `ViewPager2`를 이용하여 페이저의 페이지가 변할 때 사진이 변한다든지 텍스트의 위치가 변한다든지 하는 효과를 줬습니다.





