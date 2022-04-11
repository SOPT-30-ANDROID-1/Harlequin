package happy.mjstudio.github.presentation.repository

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import happy.mjstudio.core.presentation.util.AutoClearedValue
import happy.mjstudio.github.databinding.FragmentGithubRepositoryBinding

@AndroidEntryPoint
class GithubRepositoryFragment : Fragment() {
    private var binding: FragmentGithubRepositoryBinding by AutoClearedValue()
    private val viewModel by viewModels<GithubRepositoryViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentGithubRepositoryBinding.inflate(inflater, container, false).let {
            binding = it
            it.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel
    }
}