package happy.mjstudio.github.presentation.follower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import happy.mjstudio.core.presentation.util.AutoClearedValue
import happy.mjstudio.core.presentation.util.PixelRatio
import happy.mjstudio.core.presentation.util.itemtouchhelper.ItemTouchHelperAdapter
import happy.mjstudio.github.databinding.FragmentGithubFollowerBinding
import happy.mjstudio.github.presentation.adapter.GithubProfileAdapter

@AndroidEntryPoint
class GithubFollowerFragment(private val pixelRatio: PixelRatio) : Fragment() {
    private var binding: FragmentGithubFollowerBinding by AutoClearedValue()
    private val viewModel by viewModels<GithubFollowerViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentGithubFollowerBinding.inflate(inflater, container, false).let {
            binding = it
            it.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel

        initList()
    }

    private fun initList() {
        binding.list.run {
            adapter = GithubProfileAdapter(
                viewModel.followers,
                viewModel.listMenuOpenState,
                viewLifecycleOwner.lifecycle,
                pixelRatio,
                viewModel::onItemMoved,
                viewModel::onItemRemoved,
                viewModel::onItemMenuOpened,
                viewModel::onItemMenuClosed,
            ).also {
                ItemTouchHelperAdapter(it).attachToRecyclerView(this)
            }
        }
    }
}