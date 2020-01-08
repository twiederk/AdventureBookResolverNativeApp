package com.d20charactersheet.adventurebookresolver.nativeapp.gui

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.d20charactersheet.adventurebookresolver.nativeapp.R
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import kotlinx.android.synthetic.main.activity_main.*

import org.koin.core.KoinComponent
import org.koin.core.inject

class RestartDialog : KoinComponent {

    private val game: Game by inject()

    fun create(activity: AppCompatActivity) =
        AlertDialog.Builder(activity)
            .setMessage(R.string.restart_message)
            .setCancelable(false)
            .setPositiveButton(R.string.restart) { _, _ ->
                game.restart()
                with(activity.container) {
                    (adapter as SectionsPagerAdapter).getItem(currentItem).onResume()
                }
            }
            .setNegativeButton(R.string.cancel) { dialog: DialogInterface, _: Int -> dialog.cancel() }
            .create()

}