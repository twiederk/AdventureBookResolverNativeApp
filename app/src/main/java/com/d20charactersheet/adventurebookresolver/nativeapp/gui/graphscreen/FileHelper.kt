package com.d20charactersheet.adventurebookresolver.nativeapp.gui.graphscreen

import android.content.Context
import android.os.Environment
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths

class FileHelper(private val context: Context?) {

    fun getDownloadDirectory(): File {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    }

    fun saveInternal(bookTitle: String, fileContent: String) {
        context?.openFileOutput(bookTitle, Context.MODE_PRIVATE).use {
            it?.write(fileContent.toByteArray())
        }
    }

    fun saveExternal(bookTitle: String, fileContent: String) {
        val directory = getDownloadDirectory()
        val fileName = "$directory${File.separator}${bookTitle}.abr"
        val exportPath = Paths.get(fileName)
        exportPath.toFile().writeText(fileContent)
    }

    fun getInternalFileNames(): List<String> {
        val fileList: Array<String>? = context?.fileList()
        val internalFiles = mutableListOf<String>()
        fileList?.forEach { internalFiles.add(it) }
        return internalFiles.toList()
    }

    fun loadFile(fileName: String): List<String> {
        val path = context?.filesDir
        path?.let {
            val fileToReadFrom = File(path, fileName)
            val fileInputStream = FileInputStream(fileToReadFrom)
            return fileInputStream.bufferedReader().use(BufferedReader::readLines)
        }
        return listOf()
    }

    fun deleteFile(fileName: String) {
        val path = context?.filesDir
        path?.let {
            val fileToDelete = File(path, fileName)
            fileToDelete.delete()
        }
    }

}
