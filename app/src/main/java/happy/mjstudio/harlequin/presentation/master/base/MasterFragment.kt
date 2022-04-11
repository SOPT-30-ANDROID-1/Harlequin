package happy.mjstudio.harlequin.presentation.master.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import dagger.hilt.android.AndroidEntryPoint
import happy.mjstudio.harlequin.R
import happy.mjstudio.harlequin.databinding.FragmentMasterBinding
import happy.mjstudio.core.presentation.util.AutoClearedValue
import happy.mjstudio.harlequin.presentation.util.ext.repeatCoroutineWhenStarted
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MasterFragment : Fragment() {
    private var binding: FragmentMasterBinding by AutoClearedValue()
    private val viewModel by activityViewModels<MasterViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentMasterBinding.inflate(inflater, container, false).let {
            binding = it
            it.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.masterViewModel = viewModel

        initPager()
        initBottomTab()
    }

    private fun initPager() {
        binding.pager.run {
            offscreenPageLimit = 4
            adapter = MasterPagerAdapter(this@MasterFragment)
            registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    viewModel.onPagerIndexChanged(position)
                }

                override fun onPageScrolled(
                    position: Int, positionOffset: Float, positionOffsetPixels: Int
                ) {
                    binding.motionContainer.progress = 1f.coerceAtMost(position + positionOffset)
                }
            })
            setPageTransformer { page, position ->
                page.pivotX = if (position < 0) page.width.toFloat() else 0f
                page.pivotY = page.height * 0.5f
                page.rotationY = 50f * position
            }
        }

        repeatCoroutineWhenStarted {
            viewModel.pagerIndex.collect {
                if (binding.pager.currentItem != it) binding.pager.currentItem = it
            }
        }
    }

    private fun initBottomTab() = binding.bottomTab.run {
        fun throwUnknownTabException(): Nothing {
            throw IllegalArgumentException("what are you doing")
        }

        // holy.. I need a polymorphism
        fun indexWithId(@IdRes id: Int) = when (id) {
            R.id.githubFollowFragment -> 0
            R.id.githubFollowerFragment -> 1
            R.id.githubRepoFragment -> 2
            else -> throwUnknownTabException()
        }

        @IdRes
        fun idWithIndex(index: Int) = when (index) {
            0 -> R.id.githubFollowFragment
            1 -> R.id.githubFollowerFragment
            2 -> R.id.githubRepoFragment
            else -> throwUnknownTabException()
        }

        setOnItemSelectedListener {
            viewModel.onPagerIndexChanged(indexWithId(it.itemId))
            true
        }

        repeatCoroutineWhenStarted {
            viewModel.pagerIndex.collect {
                this@run.selectedItemId = idWithIndex(it)
            }
        }
    }
}