package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graph

import android.graphics.Bitmap
import com.d20charactersheet.adventurebookresolver.nativeapp.domain.Game
import com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen.FileHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File
import java.io.FileOutputStream

class GraphBitmap : KoinComponent {

    private val game: Game by inject()

    fun createAndSaveBitmap(graphView: GraphView) {
        val bitmap = graphView.createBitmap()
        saveBitmap(bitmap)
    }

    private fun saveBitmap(bitmap: Bitmap?) {
        val directory = FileHelper().getDownloadDirectory()
        val filename = "$directory${File.separator}${game.book.title}.png"
        FileOutputStream(filename).use { fos -> bitmap?.compress(Bitmap.CompressFormat.PNG, 100, fos) }
    }

}
