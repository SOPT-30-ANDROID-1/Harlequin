package happy.mjstudio.harlequin.presentation.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.transition.TransitionInflater
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import happy.mjstudio.harlequin.R
import happy.mjstudio.harlequin.databinding.FragmentSignUpBinding
import happy.mjstudio.harlequin.presentation.auth.AuthViewModel
import happy.mjstudio.core.presentation.util.AutoClearedValue
import happy.mjstudio.harlequin.util.onDebounceClick

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private var binding: FragmentSignUpBinding by AutoClearedValue()
    private val viewModel by hiltNavGraphViewModels<AuthViewModel>(R.id.nav_graph_auth)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialElevationScale(true)
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentSignUpBinding.inflate(inflater, container, false).let {
            binding = it
            it.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel

        binding.signUp onDebounceClick {
            viewModel.signUp()
        }
        viewModel.clearErrors()
    }

}