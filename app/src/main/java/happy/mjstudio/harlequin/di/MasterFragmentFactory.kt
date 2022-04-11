package happy.mjstudio.harlequin.di

import android.app.Activity
import androidx.fragment.app.FragmentFactory
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ActivityComponent
import happy.mjstudio.github.presentation.follow.GithubFollowFragment
import happy.mjstudio.github.presentation.follower.GithubFollowerFragment
import happy.mjstudio.github.presentation.repository.GithubRepositoryFragment
import happy.mjstudio.harlequin.presentation.master.base.MasterFragment

class MasterFragmentFactory(activity: Activity) : FragmentFactory() {

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface Injector {}

    private val entryPoint = EntryPointAccessors.fromActivity(activity, Injector::class.java)

    override fun instantiate(classLoader: ClassLoader, className: String) =
        when (loadFragmentClass(classLoader, className)) {
            MasterFragment::class.java -> MasterFragment()
            GithubFollowFragment::class.java -> GithubFollowFragment()
            GithubFollowerFragment::class.java -> GithubFollowerFragment()
            GithubRepositoryFragment::class.java -> GithubRepositoryFragment()
            else -> super.instantiate(classLoader, className)
        }
}