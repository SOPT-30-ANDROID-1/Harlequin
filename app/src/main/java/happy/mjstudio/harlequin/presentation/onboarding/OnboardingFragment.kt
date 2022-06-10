package happy.mjstudio.harlequin.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.ChangeBounds
import androidx.transition.ChangeClipBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.ChangeTransform
import androidx.transition.Fade
import androidx.transition.Scene
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import dagger.hilt.android.AndroidEntryPoint
import happy.mjstudio.core.presentation.util.AutoClearedValue
import happy.mjstudio.core.presentation.util.ext.repeatCoroutineWhenStarted
import happy.mjstudio.harlequin.R
import happy.mjstudio.harlequin.databinding.FragmentOnboardingBinding
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class OnboardingFragment : Fragment() {
    private var binding: FragmentOnboardingBinding by AutoClearedValue()
    private val viewModel by viewModels<OnboardingViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentOnboardingBinding.inflate(inflater, container, false).let {
            binding = it
            it.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel

        setSceneTransitionLogic()
    }

    private fun setSceneTransitionLogic() {
        repeatCoroutineWhenStarted {
            viewModel.endEvent.collect { navigateSignIn() }
        }

        val transition = TransitionSet().apply {
            addTransition(Fade())
            addTransition(ChangeBounds())
            addTransition(ChangeClipBounds())
            addTransition(ChangeImageTransform())
            addTransition(ChangeTransform())
        }
        val sceneList = listOf(R.layout.scene_onboarding_1, R.layout.scene_onboarding_2, R.layout.scene_onboarding_3)

        repeatCoroutineWhenStarted {
            viewModel.sceneIndex.collect {

                TransitionManager.go(
                    Scene.getSceneForLayout(binding.sceneContainer, sceneList[it], requireContext()),
                    transition
                )
            }
        }
    }

    private fun navigateSignIn() = findNavController().navigate(R.id.action_onboardingFragment_to_signInFragment)
}