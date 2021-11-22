package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getSystemService
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class ActionAddDialog(private val messageDisplay: MessageDisplay = MessageDisplay()) : KoinComponent {

    private val game: Game by inject()
    private val graphPanel: GraphPanel by inject()
    private var alertDialog: AlertDialog? = null

    @Suppress("DEPRECATION")
    fun show(context: Context) {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.dialog_action_add, null)

        setDoneActionHandler(view)
        requestFocusAndShowKeyboard(view, context)

        alertDialog?.setOnDismissListener {
            val inputMethodManager: InputMethodManager? = getSystemService(context, InputMethodManager::class.java)
            inputMethodManager?.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        }

        alertDialog = AlertDialog.Builder(context)
            .setView(view)
            .setPositiveButton("OK") { _, _ -> addAction(view) }
            .setNegativeButton("CANCEL") { dialog, _ -> dialog.cancel() }
            .create()
        alertDialog?.show()
    }

    @Suppress("DEPRECATION")
    private fun requestFocusAndShowKeyboard(view: View, context: Context) {
        val labelEditText = view.findViewById<EditText>(R.id.action_label_edit_text)
        labelEditText.requestFocus()

        val inputMethodManager: InputMethodManager? = getSystemService(context, InputMethodManager::class.java)
        inputMethodManager?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    private fun setDoneActionHandler(view: View) {
        val idEditText = view.findViewById<EditText>(R.id.action_id_edit_text)
        idEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addAction(view)
                alertDialog?.dismiss()
            }
            true
        }
    }

    internal fun addAction(view: View) {
        val actionLabel = getActionLabel(view)
        val actionId = getActionId(view)
        if (isDataValidToCreateAction(actionLabel, actionId)) {
            game.addAction(actionLabel, actionId.toInt())
            graphPanel.update()
        } else {
            val message = getErrorMessage(actionLabel, actionId)
            messageDisplay.display(view, message)
        }
    }

    private fun getActionLabel(view: View): String =
        view.findViewById<EditText>(R.id.action_label_edit_text).text.toString()

    private fun getActionId(view: View): String =
        view.findViewById<EditText>(R.id.action_id_edit_text).text.toString()

    private fun isDataValidToCreateAction(actionLabel: String, actionId: String) =
        actionLabel.isNotEmpty() && actionId.isNotEmpty() && actionId.toInt() != game.book.getEntryId()

    private fun getErrorMessage(actionLabel: String, actionId: String): String {
        if (actionLabel.isEmpty()) return "Can't create action: Label is missing"
        if (actionId.isEmpty()) return "Can't create action: Id is missing"
        if (actionId.toInt() == game.book.getEntryId()) return "Can't create action: Id is same as current node"
        return ""
    }


}


