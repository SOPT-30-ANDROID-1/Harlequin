package happy.mjstudio.core.presentation.util.itemtouchhelper

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class ItemTouchHelperAdapter(private val adapter: MoveableAdapter) : ItemTouchHelper.SimpleCallback(
    ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END, ItemTouchHelper.START
) {
    override fun isLongPressDragEnabled() = true

    override fun isItemViewSwipeEnabled() = false

    override fun onMove(
        recyclerView: RecyclerView, viewHolder: ViewHolder, target: ViewHolder
    ): Boolean {
        val pos1 = viewHolder.adapterPosition
        val pos2 = target.adapterPosition

        adapter.onItemMove(pos1, pos2)
        return true
    }

    override fun onSwiped(viewHolder: ViewHolder, direction: Int) {

    }

    fun attachToRecyclerView(rv: RecyclerView) {
        ItemTouchHelper(this).attachToRecyclerView(rv)
    }
}
