package happy.mjstudio.github.presentation.follower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import happy.mjstudio.core.presentation.util.AutoClearedValue
import happy.mjstudio.github.databinding.FragmentGithubFollowerBinding

@AndroidEntryPoint
class GithubFollowerFragment : Fragment() {
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
    }
}