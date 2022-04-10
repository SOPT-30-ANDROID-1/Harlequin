package happy.mjstudio.harlequin.presentation.auth.signin

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.ViewCompat
import androidx.core.view.updatePaddingRelative
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint
import happy.mjstudio.harlequin.R
import happy.mjstudio.harlequin.databinding.FragmentSignInBinding
import happy.mjstudio.harlequin.presentation.auth.AuthViewModel
import happy.mjstudio.harlequin.presentation.util.AutoClearedValue
import happy.mjstudio.harlequin.presentation.util.ext.getDimen
import happy.mjstudio.harlequin.presentation.util.ext.hideKeyboard
import happy.mjstudio.harlequin.presentation.util.ext.repeatCoroutineWhenStarted
import happy.mjstudio.harlequin.presentation.util.ext.showToast
import happy.mjstudio.harlequin.util.onDebounceClick
import happy.mjstudio.harlequin.util.themeswitcher.ThemeSwitcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.drop
import kotlin.random.Random

@AndroidEntryPoint
class SignInFragment(private val themeSwitcher: ThemeSwitcher) : Fragment() {
    private var binding: FragmentSignInBinding by AutoClearedValue()
    private val authViewModel by hiltNavGraphViewModels<AuthViewModel>(R.id.nav_graph_auth)
    private val signInViewModel by viewModels<SignInViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentSignInBinding.inflate(inflater, container, false).let {
            binding = it
            it.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.authViewModel = authViewModel
        binding.signInViewModel = signInViewModel

        initMath()
        thisFunctionIsSoTrash()
        initThemeSwitcherBehavior()
        initAuthBehaviors()
    }

    private val backPressedCallback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            if (signInViewModel.isMathOpen.value) {
                signInViewModel.toggleMathOpen()
            }
            isEnabled = false
        }
    }

    private fun initMath() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressedCallback)

        fun createTransform(startView: View, endView: View) = MaterialContainerTransform().apply {
            duration = 800L
            this.startView = startView
            this.endView = endView
            addTarget(endView)
            scrimColor = Color.TRANSPARENT
            setPathMotion(MaterialArcMotion())
        }

        val openTransformProvider = {
            createTransform(binding.mathFab, binding.mathCard)
        }
        val closeTransformProvider = {
            createTransform(binding.mathCard, binding.mathFab)
        }

        binding.mathCard onDebounceClick { signInViewModel.toggleMathOpen() }
        binding.mathFab onDebounceClick { signInViewModel.toggleMathOpen() }

        repeatCoroutineWhenStarted {
            signInViewModel.isMathOpen.drop(1).collect { isMathOpen ->
                if (isMathOpen) {
                    TransitionManager.beginDelayedTransition(binding.container, openTransformProvider())
                    backPressedCallback.isEnabled = true
                } else {
                    TransitionManager.beginDelayedTransition(binding.container, closeTransformProvider())
                    hideKeyboard()
                    binding.mathTextinput.clearFocus()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        backPressedCallback.remove()
    }

    private fun thisFunctionIsSoTrash() {
        binding.logoContainer.updatePaddingRelative(
            getDimen(R.dimen.side_padding), 0, getDimen(R.dimen.side_padding), 0
        )

        fun createTextView(text: String, index: Int) = TextView(context).also { tv ->
            tv.apply {
                this.text = text
                textSize = 26f
                setTextColor(Color.rgb(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)))
                setTypeface(null, Typeface.BOLD)
                id = ViewCompat.generateViewId()
                alpha = 0f
            }
            ValueAnimator.ofFloat(1f, .5f).apply {
                addUpdateListener {
                    val value = it.animatedValue as Float
                    tv.scaleX = value
                    tv.scaleY = value
                }
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE
                duration = 600
                startDelay = index * 60L
                start()
            }
            ObjectAnimator.ofFloat(tv, "alpha", 0f, 1f).apply {
                duration = 600
                startDelay = index * 60L
                start()
            }
        }

        "GREAT IDEAS NEVER RUST".forEachIndexed { index, c ->
            createTextView(c.toString(), index).run {
                binding.logoContainer.addView(this)
                binding.logoFlow.addView(this)
            }
        }
    }

    private fun initThemeSwitcherBehavior() = binding.themeFab onDebounceClick { themeSwitcher.switchMode() }

    private fun initAuthBehaviors() {
        binding.signIn onDebounceClick { authViewModel.signIn() }
        binding.signUp onDebounceClick {
            findNavController().navigate(
                R.id.action_signInFragment_to_signUpFragment,
                null,
                null,
                FragmentNavigatorExtras(binding.idLayout to "id_layout", binding.pwLayout to "pw_layout")
            )
        }
        repeatCoroutineWhenStarted {
            authViewModel.unknownExceptionEvent.collect(::showToast)
        }
    }

}