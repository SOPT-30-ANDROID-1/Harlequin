package happy.mjstudio.harlequin.di

import android.app.Activity
import androidx.fragment.app.FragmentFactory
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import happy.mjstudio.harlequin.auth.presentation.signin.SignInFragment
import happy.mjstudio.harlequin.auth.presentation.signup.SignUpFragment
import happy.mjstudio.harlequin.util.themeswitcher.ThemeSwitcher

class AuthFragmentFactory(activity: Activity) : FragmentFactory() {

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface Injector {
        @ActivityScoped
        fun themeSwitcher(): ThemeSwitcher
    }

    private val entryPoint = EntryPointAccessors.fromActivity(activity, Injector::class.java)

    override fun instantiate(classLoader: ClassLoader, className: String) =
        when (loadFragmentClass(classLoader, className)) {
            SignInFragment::class.java -> SignInFragment(entryPoint.themeSwitcher())
            SignUpFragment::class.java -> SignUpFragment()
            else -> super.instantiate(classLoader, className)
        }
}