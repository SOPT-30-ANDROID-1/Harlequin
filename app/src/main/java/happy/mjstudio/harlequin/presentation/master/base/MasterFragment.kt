package happy.mjstudio.harlequin.presentation.master.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import dagger.hilt.android.AndroidEntryPoint
import happy.mjstudio.harlequin.databinding.FragmentMasterBinding
import happy.mjstudio.harlequin.presentation.util.AutoClearedValue
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
                    binding.motionContainer.progress = (position + positionOffset) / (adapter!!.itemCount - 1)
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
}