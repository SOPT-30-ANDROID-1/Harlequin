package happy.mjstudio.github.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import happy.mjstudio.github.data.entity.GithubRepo
import happy.mjstudio.github.databinding.ItemGithubRepoBinding

class GithubRepoAdapter : RecyclerView.Adapter<GithubRepoAdapter.GithubRepoHolder>() {
    private val diff = object : DiffUtil.ItemCallback<GithubRepo>() {
        override fun areItemsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diff)

    fun submitItems(items: List<GithubRepo>) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubRepoHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGithubRepoBinding.inflate(inflater, parent, false)

        return GithubRepoHolder(binding)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: GithubRepoHolder, position: Int) = holder.bind(differ.currentList[position])

    inner class GithubRepoHolder(private val binding: ItemGithubRepoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GithubRepo) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
}

@BindingAdapter("github_repo_items")
fun RecyclerView.setItems(items: List<GithubRepo>?) {
    if (items == null) return
    (adapter as? GithubRepoAdapter)?.run {
        this.submitItems(items)
    }
}