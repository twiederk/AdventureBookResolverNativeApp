package com.d20charactersheet.adventurebookresolver.nativeapp

import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.core.KoinComponent
import org.koin.core.inject

class InventoryPanel : Panel {

    internal lateinit var itemLabelEditText: EditText
    private lateinit var itemAddButton: Button
    internal lateinit var inventoryRecyclerView: RecyclerView
    internal var itemTouchHelper: ItemTouchHelper? = null

    override fun create(rootView: View) {
        itemLabelEditText = rootView.findViewById(R.id.item_label_edit_text)
        createItemAddButton(rootView)
        createActionMoveRecyclerView(rootView)
    }

    private fun createItemAddButton(rootView: View) {
        itemAddButton = rootView.findViewById(R.id.item_add_button)
        itemAddButton.setOnClickListener(ItemAddOnClickListener())
    }

    private fun createActionMoveRecyclerView(rootView: View) {
        val inventoryAdapter = InventoryAdapter()
        inventoryRecyclerView = rootView.findViewById<RecyclerView>(R.id.item_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(rootView.context)
            adapter = inventoryAdapter
        }
        if (itemTouchHelper == null) {
            itemTouchHelper = ItemTouchHelper(InventoryDeleteOnSwipeListener(inventoryAdapter))
        }
        itemTouchHelper?.attachToRecyclerView(inventoryRecyclerView)

    }


    override fun update() {
        inventoryRecyclerView.adapter?.notifyDataSetChanged()
    }

    fun getItem() = itemLabelEditText.text.toString()

    fun clear() = itemLabelEditText.setText("")

}


class ItemAddOnClickListener : View.OnClickListener, KoinComponent {

    private val game: Game by inject()
    private val inventoryPanel: InventoryPanel by inject()

    override fun onClick(v: View?) {
        val item = inventoryPanel.getItem()
        if (item.isNotEmpty()) {
            game.addItemToInventory(item)
            inventoryPanel.clear()
            inventoryPanel.update()
        }
    }

}

