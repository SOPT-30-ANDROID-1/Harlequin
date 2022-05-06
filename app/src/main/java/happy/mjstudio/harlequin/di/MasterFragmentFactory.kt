package happy.mjstudio.harlequin.di

import android.app.Activity
import androidx.fragment.app.FragmentFactory
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import happy.mjstudio.core.presentation.util.PixelRatio
import happy.mjstudio.github.presentation.follow.GithubFollowFragment
import happy.mjstudio.github.presentation.follower.GithubFollowerFragment
import happy.mjstudio.github.presentation.home.HomeFragment
import happy.mjstudio.github.presentation.repository.GithubRepositoryFragment
import happy.mjstudio.harlequin.presentation.camera.CameraFragment
import happy.mjstudio.harlequin.presentation.master.base.MasterFragment
import happy.mjstudio.harlequin.presentation.profile.ProfileFragment

class MasterFragmentFactory(activity: Activity) : FragmentFactory() {

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface Injector {
        @ActivityScoped
        fun pixelRatio(): PixelRatio
    }

    private val entryPoint = EntryPointAccessors.fromActivity(activity, Injector::class.java)

    override fun instantiate(classLoader: ClassLoader, className: String) =
        when (loadFragmentClass(classLoader, className)) {
            MasterFragment::class.java -> MasterFragment()
            ProfileFragment::class.java -> ProfileFragment()
            HomeFragment::class.java -> HomeFragment()
            CameraFragment::class.java -> CameraFragment()
            GithubFollowFragment::class.java -> GithubFollowFragment(entryPoint.pixelRatio())
            GithubFollowerFragment::class.java -> GithubFollowerFragment(entryPoint.pixelRatio())
            GithubRepositoryFragment::class.java -> GithubRepositoryFragment()
            else -> super.instantiate(classLoader, className)
        }
}