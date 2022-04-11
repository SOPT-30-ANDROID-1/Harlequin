package happy.mjstudio.harlequin.presentation.master.base

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import happy.mjstudio.harlequin.di.MasterFragmentFactory
import happy.mjstudio.harlequin.presentation.master.githubfollow.GithubFollowFragment

class MasterPagerAdapter(private val fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        val classLoader = fragment.requireActivity().classLoader
        val factory = MasterFragmentFactory(fragment.requireActivity())
        return when (position) {
            0 -> factory.instantiate(classLoader, GithubFollowFragment::class.java.name)
            1 -> factory.instantiate(classLoader, GithubFollowFragment::class.java.name)
            else -> throw RuntimeException("What the...")
        }
    }
}