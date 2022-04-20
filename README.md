# Harlequin

> 내가 사랑하는 남자가 완전히 미친놈이니까 나도 미쳐야겠네.

<img src="https://user-images.githubusercontent.com/33388801/161442672-8605c571-b0d1-4d28-af8f-79a06ddd7ee0.png" width=250/>

# Seminar 2

## RecyclerView

<img src="https://user-images.githubusercontent.com/33388801/164182464-0f5d969b-bfeb-492c-9145-ad3500948c92.gif" width=300/>

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

## 도전 과제

### 3-1

Lifecycle observer와 Kotlin property delegation을 이용해 lifecycle에 바인딩 객체를 binding시켜 뷰가 파괴되는 생명주기에서 참조를 직접적으로 끊어줄 필요가 없습니다. 

`AutoClearedValue.kt`
```kotlin
class AutoClearedValue<T : Any> : ReadWriteProperty<Fragment, T>, DefaultLifecycleObserver {
    private var _value: T? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
        _value ?: throw IllegalStateException("Trying to call an auto-cleared value outside of the view lifecycle.")

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        thisRef.viewLifecycleOwner.lifecycle.removeObserver(this)
        _value = value
        thisRef.viewLifecycleOwner.lifecycle.addObserver(this)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        _value = null
    }
}
```

`XXXFragment.kt`
```kotlin
@AndroidEntryPoint
class GithubDetailFragment : Fragment() {
    private var binding: FragmentGithubDetailBinding by AutoClearedValue()
```

### 3-2
notifyDataSetChanged는 계속 모든 아이템을 새롭게 렌더링시킨다는 문제점이 있습니다.
AsyncListDiffer나 이를 래핑한 ListAdapter를 이용해 데이터의 변화만을 빠르게 계산해 UI에 반영해주는 유틸리티들을 사용해 개선할 수 있습니다.




