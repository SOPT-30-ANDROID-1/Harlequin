package happy.mjstudio.github.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import happy.mjstudio.core.presentation.util.PixelRatio
import happy.mjstudio.core.presentation.util.debug
import happy.mjstudio.core.presentation.util.itemtouchhelper.MoveableAdapter
import happy.mjstudio.core.presentation.util.itemtouchhelper.SwipeMenuTouchListener
import happy.mjstudio.core.presentation.util.itemtouchhelper.SwipeMenuTouchListener.Callback
import happy.mjstudio.core.presentation.util.onDebounceClick
import happy.mjstudio.github.data.entity.GithubProfile
import happy.mjstudio.github.databinding.ItemGithubProfileBinding
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GithubProfileAdapter(
    private val dataFlow: StateFlow<List<GithubProfile>>,
    private val menuOpenStateFlow: StateFlow<List<Boolean>>,
    private val lifecycle: Lifecycle,
    private val pixelRatio: PixelRatio,
    private val onItemMoveCallback: (Int, Int) -> Unit,
    private val onItemRemoved: (Int) -> Unit,
    private val onItemMenuOpened: (Int) -> Unit,
    private val onItemMenuClosed: (Int) -> Unit,
) : RecyclerView.Adapter<GithubProfileAdapter.GithubFollowerHolder>(), MoveableAdapter {
    private val diff = object : DiffUtil.ItemCallback<GithubProfile>() {
        override fun areItemsTheSame(oldItem: GithubProfile, newItem: GithubProfile): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GithubProfile, newItem: GithubProfile): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diff)

    init {
        lifecycle.coroutineScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                dataFlow.collect {
                    differ.submitList(it)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubFollowerHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGithubProfileBinding.inflate(inflater, parent, false)

        return GithubFollowerHolder(binding)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: GithubFollowerHolder, position: Int) =
        holder.bind(differ.currentList[position], menuOpenStateFlow.value[position])

    inner class GithubFollowerHolder(private val binding: ItemGithubProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val menuWidth = pixelRatio.toPixel(60).toFloat()
        private var transX
            get() = binding.container.translationX
            set(value) {
                binding.container.translationX = value
            }

        init {
            setOnSwipeMenuTouchListener()
            setOnRemoveMenuClickListener()
        }

        private fun setOnSwipeMenuTouchListener() = binding.container.setOnTouchListener(
            SwipeMenuTouchListener(menuWidth, object : Callback {
                override fun onContentXChanged(x: Float) {
                    transX = x
                }

                override fun onContentXAnimated(x: Float) {
                    animateTranslationX(x)
                }

                override fun onMenuOpened() {
                    onItemMenuOpened(layoutPosition)
                }

                override fun onMenuClosed() {
                    onItemMenuClosed(layoutPosition)
                }
            })
        )

        private fun animateTranslationX(to: Float) {
            binding.container.animate().translationX(to).apply {
                duration = 100L
            }
        }

        private fun setOnRemoveMenuClickListener() = binding.delete onDebounceClick {
            onItemRemoved(layoutPosition)
        }

        fun bind(item: GithubProfile, isMenuOpen: Boolean) {
            transX = if (isMenuOpen) -menuWidth else 0f

            binding.item = item
            binding.executePendingBindings()
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) = onItemMoveCallback(fromPosition, toPosition)
}