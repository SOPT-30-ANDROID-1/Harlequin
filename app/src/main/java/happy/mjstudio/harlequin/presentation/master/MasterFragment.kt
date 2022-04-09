package happy.mjstudio.harlequin.presentation.master

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.thedeanda.lorem.LoremIpsum
import dagger.hilt.android.AndroidEntryPoint
import happy.mjstudio.harlequin.databinding.FragmentMasterBinding
import happy.mjstudio.harlequin.presentation.util.AutoClearedValue

@AndroidEntryPoint
class MasterFragment : Fragment() {

    private var binding: FragmentMasterBinding by AutoClearedValue()
    private val viewModel by activityViewModels<MasterViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentMasterBinding.inflate(inflater, container, false).let {
            binding = it
            it.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.masterViewModel = viewModel

        setMarqueeText()
    }

    private fun setMarqueeText() {
        binding.marquee.text = LoremIpsum.getInstance().getWords(1000)
    }

}