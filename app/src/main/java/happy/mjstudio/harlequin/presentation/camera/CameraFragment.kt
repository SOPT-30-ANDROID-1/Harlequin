package happy.mjstudio.harlequin.presentation.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import happy.mjstudio.core.presentation.util.AutoClearedValue
import happy.mjstudio.core.presentation.util.onDebounceClick
import happy.mjstudio.harlequin.databinding.FragmentCameraBinding

@AndroidEntryPoint
class CameraFragment : Fragment() {
    private var binding: FragmentCameraBinding by AutoClearedValue()
    private val viewModel by viewModels<CameraViewModel>()

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        it?.run {
            binding.imageView.setImageURI(this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentCameraBinding.inflate(inflater, container, false).let {
            binding = it
            it.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel

        configureGalleryLogic()
    }

    private fun configureGalleryLogic() {
        binding.open onDebounceClick {
            launcher.launch("image/*")
        }
    }
}