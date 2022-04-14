package happy.mjstudio.github.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import happy.mjstudio.core.presentation.util.debug
import happy.mjstudio.github.data.entity.GithubProfile
import happy.mjstudio.github.databinding.ItemGithubProfileBinding

class GithubProfileAdapter : RecyclerView.Adapter<GithubProfileAdapter.GithubFollowerHolder>() {
    private val diff = object : DiffUtil.ItemCallback<GithubProfile>() {
        override fun areItemsTheSame(oldItem: GithubProfile, newItem: GithubProfile): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GithubProfile, newItem: GithubProfile): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diff)

    fun submitItems(items: List<GithubProfile>) {
        debug(items)
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubFollowerHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGithubProfileBinding.inflate(inflater, parent, false)

        return GithubFollowerHolder(binding)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: GithubFollowerHolder, position: Int) =
        holder.bind(differ.currentList[position])

    inner class GithubFollowerHolder(private val binding: ItemGithubProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GithubProfile) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
}

@BindingAdapter("recyclerview_github_profile_items")
fun RecyclerView.setItems(items: List<GithubProfile>?) {
    if (items == null) return
    (adapter as? GithubProfileAdapter)?.run {
        this.submitItems(items)
    }
}