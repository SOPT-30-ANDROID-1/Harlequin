package happy.mjstudio.harlequin.presentation.master.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.viewpager2.adapter.FragmentStateAdapter
import happy.mjstudio.github.presentation.home.HomeFragment
import happy.mjstudio.harlequin.presentation.camera.CameraFragment
import happy.mjstudio.harlequin.presentation.profile.ProfileFragment

class MasterPagerAdapter(private val factory: FragmentFactory, private val fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        val classLoader = fragment.requireActivity().classLoader
        return when (position) {
            0 -> factory.instantiate(classLoader, ProfileFragment::class.java.name)
            1 -> factory.instantiate(classLoader, HomeFragment::class.java.name)
            2 -> factory.instantiate(classLoader, CameraFragment::class.java.name)
            else -> throw RuntimeException("What the...")
        }
    }
}