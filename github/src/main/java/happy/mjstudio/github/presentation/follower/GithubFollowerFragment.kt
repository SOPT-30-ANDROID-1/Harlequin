package happy.mjstudio.github.presentation.follower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import happy.mjstudio.core.presentation.util.AutoClearedValue
import happy.mjstudio.core.presentation.util.PixelRatio
import happy.mjstudio.core.presentation.util.itemtouchhelper.ItemTouchHelperAdapter
import happy.mjstudio.github.data.entity.GithubProfile
import happy.mjstudio.github.databinding.FragmentGithubFollowerBinding
import happy.mjstudio.github.di.GithubNavigationActionProvider
import happy.mjstudio.github.presentation.adapter.GithubProfileAdapter
import javax.inject.Inject

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
                ::navigateDetail,
            ).also {
                ItemTouchHelperAdapter(it).attachToRecyclerView(this)
            }
        }
    }

    @Inject
    lateinit var navigationActionProvider: GithubNavigationActionProvider

    private fun navigateDetail(profile: GithubProfile, view: View) {
        findNavController().navigate(
            navigationActionProvider.detailAction(profile), FragmentNavigatorExtras(
                view to "thumbnail"
            )
        )
    }
}