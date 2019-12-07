package com.d20charactersheet.adventurebookresolver.nativeapp.gui.inventory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.d20charactersheet.adventurebookresolver.core.domain.Item
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.koin.core.KoinComponent
import org.koin.core.inject

class ItemAdapter : RecyclerView.Adapter<ItemViewHolder>(), KoinComponent {

    private val game: Game by inject()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.listitem_item, parent, false)
        return ItemViewHolder(
            rootView
        )
    }

    override fun onBindViewHolder(itemViewHolder: ItemViewHolder, position: Int) {
        val item = game.book.inventory.items[position]
        itemViewHolder.setItem(item)
    }

    override fun getItemCount() = game.getNumberOfItems()

    fun deleteItem(adapterPosition: Int) = game.removeItemFromInventory(adapterPosition)

}

class ItemViewHolder(val rootView: View) : RecyclerView.ViewHolder(rootView) {

    private val itemTextView: TextView = rootView.findViewById(R.id.item_text_view)

    fun setItem(item: Item) {
        itemTextView.text = item.name
    }
}

class ItemDeleteOnSwipeListener(private val itemAdapter: ItemAdapter) : OnSwipeListener() {

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        itemAdapter.deleteItem(viewHolder.adapterPosition)
        itemAdapter.notifyItemRemoved(viewHolder.adapterPosition)
    }

}
