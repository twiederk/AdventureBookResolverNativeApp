package com.d20charactersheet.adventurebookresolver.nativeapp.gui

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RestartDialog : KoinComponent {

    private val game: Game by inject()

    fun create(activity: AppCompatActivity): AlertDialog {
        val alertDialog = AlertDialog.Builder(activity)
            .setMessage(R.string.restart_message)
            .setCancelable(false)
            .setPositiveButton(R.string.restart) { _, _ ->
                game.restart()
            }
            .setNegativeButton(R.string.cancel) { dialog: DialogInterface, _: Int -> dialog.cancel() }
            .create()
        return alertDialog
    }

}