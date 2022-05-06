package happy.mjstudio.github.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.Tab
import dagger.hilt.android.AndroidEntryPoint
import happy.mjstudio.core.presentation.util.AutoClearedValue
import happy.mjstudio.core.presentation.util.ext.repeatCoroutineWhenStarted
import happy.mjstudio.github.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding by AutoClearedValue()
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentHomeBinding.inflate(inflater, container, false).let {
            binding = it
            it.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel

        initPager()
        initTabLayout()
    }

    private fun initPager() {
        binding.pager.run {
            offscreenPageLimit = 4
            isUserInputEnabled = false
            adapter = HomePagerAdapter(requireActivity().supportFragmentManager.fragmentFactory, this@HomeFragment)
            registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    viewModel.onPagerIndexChanged(position)
                }
            })
            setPageTransformer { page, position ->
                page.pivotX = if (position < 0) page.width.toFloat() else 0f
                page.pivotY = page.height * 0.5f
                page.rotationY = 50f * position
                page.alpha = 1 - 1f.coerceAtMost(kotlin.math.abs(position))
            }
        }

        repeatCoroutineWhenStarted {
            viewModel.pagerIndex.collect {
                binding.pager.currentItem = it
            }
        }
    }

    private fun initTabLayout() {
        binding.tabLayout.run {
            addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: Tab) {
                    viewModel.onPagerIndexChanged(tab.position)
                }

                override fun onTabUnselected(tab: Tab?) {
                }

                override fun onTabReselected(tab: Tab?) {
                }
            })
        }
    }
}