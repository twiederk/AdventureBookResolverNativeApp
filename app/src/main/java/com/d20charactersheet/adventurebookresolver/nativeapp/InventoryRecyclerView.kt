package com.d20charactersheet.adventurebookresolver.nativeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.d20charactersheet.adventurebookresolver.core.domain.Item
import org.koin.core.KoinComponent
import org.koin.core.inject

class InventoryAdapter : RecyclerView.Adapter<InventoryViewHolder>(), KoinComponent {

    private val game: Game by inject()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.listitem_inventory, parent, false)
        return InventoryViewHolder(rootView)
    }

    override fun onBindViewHolder(inventoryViewHolder: InventoryViewHolder, position: Int) {
        val item = game.book.inventory.items[position]
        inventoryViewHolder.setItem(item)
    }

    override fun getItemCount() = game.getNumberOfItems()

    fun deleteItem(adapterPosition: Int) = game.removeItemFromInventory(adapterPosition)

}

class InventoryViewHolder(val rootView: View) : RecyclerView.ViewHolder(rootView) {

    private val itemTextView: TextView = rootView.findViewById(R.id.item_text_view)

    fun setItem(item: Item) {
        itemTextView.text = item.name
    }
}

class InventoryDeleteOnSwipeListener(private val inventoryAdapter: InventoryAdapter) : OnSwipeListener() {

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        inventoryAdapter.deleteItem(viewHolder.adapterPosition)
        inventoryAdapter.notifyItemRemoved(viewHolder.adapterPosition)
    }

}
