package com.d20charactersheet.adventurebookresolver.nativeapp.gui

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.d20charactersheet.adventurebookresolver.nativeapp.R

class DialogBuilder {

    fun create(activity: AppCompatActivity, listener: DialogInterface.OnClickListener) =
        AlertDialog.Builder(activity)
            .setMessage(R.string.restart_message)
            .setCancelable(false)
            .setPositiveButton(R.string.restart, listener)
            .setNegativeButton(R.string.cancel) { dialog: DialogInterface, _: Int -> dialog.cancel() }
            .create()

}