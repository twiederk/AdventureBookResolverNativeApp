package com.d20charactersheet.adventurebookresolver.nativeapp

import android.os.Environment
import java.io.File

class FileHelper {

    fun getDownloadDirectory(): File {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    }
}
