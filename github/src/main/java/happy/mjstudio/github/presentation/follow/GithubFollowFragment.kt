package happy.mjstudio.github.presentation.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import happy.mjstudio.core.presentation.util.AutoClearedValue
import happy.mjstudio.core.presentation.util.onDebounceClick
import happy.mjstudio.github.databinding.FragmentGithubFollowBinding
import happy.mjstudio.github.presentation.adapter.GithubProfileAdapter

@AndroidEntryPoint
class GithubFollowFragment : Fragment() {
    private var binding: FragmentGithubFollowBinding by AutoClearedValue()
    private val viewModel by viewModels<GithubFollowViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentGithubFollowBinding.inflate(inflater, container, false).let {
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
            layoutManager = LinearLayoutManager(requireContext())
            adapter = GithubProfileAdapter()
        }
        binding.fab onDebounceClick {
            changeListLayoutManager()
        }
    }

    private fun changeListLayoutManager() {
        if (binding.list.layoutManager is GridLayoutManager) {
            binding.list.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        } else {
            binding.list.layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }
}